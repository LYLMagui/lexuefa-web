package com.lexuefa.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lexuefa.comment.exception.ApiException;
import com.lexuefa.controller.reqEntity.LoginReq;
import com.lexuefa.entity.user.LoginUser;
import com.lexuefa.dao.user.UserDao;
import com.lexuefa.entity.user.User;
import com.lexuefa.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 自定义UserDetailService实现类实现用户认证逻辑
 * @author ukir
 * @date 2023/05/02 21:37
 **/
@Service
@Slf4j
public class UserDetailsServiceImpl implements MyUserDetailsService {
    
    @Resource
    private UserDao userDao;
    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.secret}")
    private String secret;

    @Value("${Rsa.privateKey}")
    private String privateKey;

    @Value("${Rsa.publicKey}")
    private String publicKey;
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JSONObject jsonObject = null;
        try{
            jsonObject = JSONUtil.parseObj(username);
        }catch (Exception e){
            throw new RuntimeException("json对象转换错误：" + e.getMessage());
        }
        log.info("registerType：{}",jsonObject.getStr("registerType").toString());
        if("wx".equals(jsonObject.getStr("registerType"))){
            //获取openId
            Map<String,String> map = jsonObject.toBean(Map.class);
            String openId = (String) map.get("openId");
            String sessionKey = (String) map.get("sessionKey");
            User user = userDao.searchIdByOpenId(openId);
            //认证，校验用户是否存在
            if(Objects.isNull(user)){
                try {
                    map.put("password",map.get("sessionKey"));
                    //注册用户
                    userDao.saveUserByWx(map);
                    user = userDao.searchIdByOpenId(openId);
                }catch (Exception e){
                    log.error("注册失败：{}",e.getMessage());
                }
            }
            return new LoginUser(user);
        }else if("zh".equals(jsonObject.getStr("registerType"))){
            LoginReq loginReq = jsonObject.toBean(LoginReq.class);
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUserName,loginReq.getUserName());
            User user = userDao.selectOne(queryWrapper);
            if(Objects.isNull(user)){
                throw new RuntimeException("用户不存在");
            }
            //把数据封装成UserDetail返回
            return new LoginUser(user);
        }else {
            throw new ApiException("非法途径登录");
        }
    }


    /**
     * 查询用户信息
     *
     * @param loginReq
     * @return
     */
    @Override
    public UserDetails loadUserByOpenId(LoginReq loginReq) {
        return null;
    }

    /**
     * 获取OpenId
     * @param code
     * @return
     */
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
        map.put("opendI",openId);
        map.put("sessionKey",sessionKey);

        //判断openId是否为空
        if (StringUtils.isEmpty(openId)) {
            throw new ApiException("获取openId失败");
        }
        log.info("openid:{}", openId);
        return map;
    }
}
