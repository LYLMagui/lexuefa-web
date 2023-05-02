package com.lexuefa.service.impl;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lexuefa.comment.exception.ApiException;
import com.lexuefa.controller.reqEntity.LoginReq;
import com.lexuefa.dao.user.UserDao;
import com.lexuefa.entity.user.User;
import com.lexuefa.service.UserService;
import com.lexuefa.util.PassowrdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * UserService实现类
 *
 * @author ukir
 * @date 2023/04/28 22:56
 **/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

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

    /**
     * @param loginReq
     * @return
     */
    @Override
    public User selectOne(LoginReq loginReq) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, loginReq.getUserName());
        return userDao.selectOne(queryWrapper);
    }

    /**
     * 用户登录
     *
     * @param loginReq
     * @return
     */
    @Override
    public Integer login(LoginReq loginReq) {
        //判断用户登录方式
        if ("wx".equals(loginReq.getRegisterType())) {
            String openId = getOpenId(loginReq.getCode());
            Integer id = userDao.searchIdByOpenId(openId);
            //判断用户是否存在，不存在则注册
            if (id == null) {
                try {
                    userDao.saveUser(loginReq, openId,null);
                } catch (Exception e) {
                    throw new RuntimeException("注册失败，原因：" + e.getMessage());
                }
            }
            return userDao.searchIdByOpenId(openId);
        } else if("zh".equals(loginReq.getRegisterType())){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUserName, loginReq.getUserName());
            User user = userDao.selectOne(queryWrapper);
            if (user == null) {
                throw new ApiException("账号不存在");
            }else {
                //查询密码是否正确
                String inputPassword = null;
                try {
                    //密码解密
                    RSA rsa = new RSA(privateKey,publicKey);
                    inputPassword = rsa.decryptStr(loginReq.getPassword(), KeyType.PrivateKey);
                    log.info("密码原文：{}",inputPassword);
                }catch (Exception e){
                    throw new ApiException("系统错误");
                }
                //判断输入的密码加盐加密后是否和数据库中的密文相等
                boolean result = PassowrdUtil.checkPassword(inputPassword, user.getPassword());
                log.info("密码校验结果：{}",result);
                if(result){
                    return user.getId();
                }else {
                    return null;
                }
            }
        }else {
            throw new ApiException("非法途径登录");
        }
    }

    /**
     * 账号注册
     *
     * @param loginReq
     * @return
     */
    @Override
    public Integer register(LoginReq loginReq) {
        Integer count = userDao.searchIdByUserName(loginReq.getUserName());
        if(count != null && count != 0){
            throw new ApiException("用户已存在");
        }
        Integer registCount = null;
        try {
            //解密密码
            RSA rsa = new RSA(privateKey,publicKey);
            String decPassword = rsa.decryptStr(loginReq.getPassword(), KeyType.PrivateKey);
            
            //使用加盐哈希加盐加密
            String salt = PassowrdUtil.generateSalt();
            String saltPassword = PassowrdUtil.encrypt(decPassword, salt);
            loginReq.setPassword(saltPassword);
            registCount = userDao.saveUser(loginReq, null,salt);
        } catch (Exception e) {
            log.error("注册失败：" + e.getMessage());
            throw new ApiException("注册失败");
        }
        return registCount;
    }

    /**
     * 获取OpenId
     *
     * @param code
     * @return
     */
    private String getOpenId(String code) {
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

        //判断openId是否为空
        if (StringUtils.isEmpty(openId)) {
            throw new ApiException("获取openId失败");
        }
        log.info("openid:{}", openId);
        return openId;
    }

}
