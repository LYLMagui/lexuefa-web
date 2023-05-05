package com.lexuefa.service.impl;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lexuefa.comment.exception.ApiException;
import com.lexuefa.comment.result.ResultObject;
import com.lexuefa.controller.reqEntity.LoginReq;
import com.lexuefa.dao.user.UserDao;
import com.lexuefa.entity.user.LoginUser;
import com.lexuefa.entity.user.User;
import com.lexuefa.service.LoginService;
import com.lexuefa.service.UserService;
import com.lexuefa.utils.JwtTokenUtil;
import com.lexuefa.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 用户登录认证Service实现类
 *
 * @author ukir
 * @date 2023/05/02 22:00
 **/
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;

    @Resource
    private RedisTemplate<String,String> redisTemplate;
    
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.secret}")
    private String secret;
    @Value("${Rsa.privateKey}")
    private String privateKey;

    @Value("${Rsa.publicKey}")
    private String publicKey;
    
    @Resource
    private UserDao userDao;

    @Resource
    private PasswordEncoder passwordEncoder;

    //redis缓存过期时间
    @Value("${Redis.cacheExpire}")
    private int cacheExpire;

    /**
     * 用户登录
     *
     * @param reqJson
     * @return
     */
    @Override
    public ResultObject<Map<String,String>> login(String reqJson) {
        //请求参数转化为json对象
        JSONObject jsonObject = JSONUtil.parseObj(reqJson);
        LoginReq loginReq = jsonObject.toBean(LoginReq.class);
        UsernamePasswordAuthenticationToken authenticationToken = null;
        //判断用户登录方式
        if ("wx".equals(loginReq.getRegisterType())) {
            Map<String, String> map = getOpenId(loginReq.getCode());
            String openId = map.get("openId");
            String sessionKey = map.get("sessionKey");
            map.put("nickName",loginReq.getNickName());
            map.put("avatarUrl",loginReq.getAvatarUrl());
            map.put("registerType",loginReq.getRegisterType());
            map.put("sessionKey",passwordEncoder.encode(sessionKey));
            String jsonMap = JSONUtil.toJsonStr(map);
            authenticationToken = new UsernamePasswordAuthenticationToken(jsonMap, sessionKey);
        } else if ("zh".equals(loginReq.getRegisterType())) {
            //密文解密
            RSA rsa = new RSA(privateKey,publicKey);
            String decPassword = rsa.decryptStr(loginReq.getPassword(), KeyType.PrivateKey);
            authenticationToken = new UsernamePasswordAuthenticationToken(reqJson, decPassword);
        }
        //通过UsernamePasswordAuthenticationToken获取用户密码
        Authentication authenticate;
        try {
            //AuthenticationManager委托机制对authenticationToken 进行用户认证
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            log.error("登录失败：{}",e.getMessage());
            throw new ApiException("账号或密码错误");
        }

        //如果认证通过，拿到这个当前登录用户信息
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        //如果认证通过，使用user生成jwt
        Integer userid = loginUser.getUser().getId();
        String token = jwtTokenUtil.generateToken(userid);
        Map<String, String> map = new HashMap<>();
        map.put("code", "200");
        map.put("message", "登录成功");
        map.put("token", token);
        //将token存储到redis中
//        redisCache.setCacheObject("login:" + userid, token, cacheExpire, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("login:" + userid,token,cacheExpire,TimeUnit.HOURS);
        return ResultObject.success("登录成功",map);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @Override
    public ResultObject<Map<String,String>> logout() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();
        redisCache.deleteObject("login:" + loginUser.getUser().getId());
        Map<String, String> map = new HashMap<>();
        map.put("code", "200");
        map.put("message", "退出成功");
        return ResultObject.success(map);
    }

    /**
     * 用户注册
     *
     * @param loginReq
     * @return
     */
    @Override
    public ResultObject<Map<String, String>> register(LoginReq loginReq) {
        User user = userDao.searchIdByUserName(loginReq.getUserName());
        Map<String,String> map = new HashMap<>();
        if(!Objects.isNull(user)){
            throw new ApiException("账号已注册");
        }
        try {
            //解密密文
            RSA rsa = new RSA(privateKey,publicKey);
            String decPassword = rsa.decryptStr(loginReq.getPassword(), KeyType.PrivateKey);

            //使用SpringSecurity提供的方法加密
            String saltPassword = passwordEncoder.encode(decPassword);
            loginReq.setPassword(saltPassword);
            userDao.saveUser(loginReq, null);
        } catch (Exception e) {
            log.error("注册失败：" + e.getMessage());
            throw new ApiException("注册失败");
        }
        map.put("code","200");
        map.put("message","注册成功");
        map.put("data",null);
        return ResultObject.success("注册成功",map);

    }


    private Map<String, String> getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap<String, Object> param = new HashMap<>();
        param.put("appid", appId);
        param.put("secret", secret);
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");

        if (StringUtils.isEmpty(code)) {
            log.error("临时登录凭证为空");
            throw new ApiException("临时登录凭证为空");
        }

        //发起请求获取openId
        String resp = HttpUtil.post(url, param);
        JSONObject parse = JSONUtil.parseObj(resp);
        String openId = parse.getStr("openid");
        String sessionKey = parse.getStr("session_key");
        
        Map<String, String> map = new HashMap<>();
        map.put("openId",openId);
        map.put("sessionKey",sessionKey);

        
        //判断openId是否为空
        if (StringUtils.isEmpty(openId)) {
            throw new ApiException("获取openId失败");
        }
        log.info("openid:{}", openId);
        return map;
    }
}
