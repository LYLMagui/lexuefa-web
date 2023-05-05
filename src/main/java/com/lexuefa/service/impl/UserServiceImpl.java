package com.lexuefa.service.impl;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lexuefa.comment.exception.ApiException;
import com.lexuefa.comment.result.ResultObject;
import com.lexuefa.controller.reqEntity.LoginReq;
import com.lexuefa.dao.user.UserDao;
import com.lexuefa.entity.user.LoginUser;
import com.lexuefa.entity.user.User;
import com.lexuefa.service.UserService;
import com.lexuefa.utils.JwtTokenUtil;
import com.lexuefa.utils.PassowrdUtil;
import com.lexuefa.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    
    @Resource
    private PasswordEncoder passwordEncoder;

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.secret}")
    private String secret;

    @Value("${Rsa.privateKey}")
    private String privateKey;
    
    @Value("${Rsa.publicKey}")
    private String publicKey;
    
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    
    @Resource
    private RedisCache redisCache;

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


}
