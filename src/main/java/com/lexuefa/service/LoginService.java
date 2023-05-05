package com.lexuefa.service;


import com.lexuefa.comment.result.ResultObject;
import com.lexuefa.controller.reqEntity.LoginReq;
import com.lexuefa.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户登录认证Service
 */
public interface LoginService {

    /**
     * 用户登录
     * @param reqJson
     * @return
     */
    ResultObject<Map<String,String>> login(String reqJson);

    /**
     * 退出登录
     * @return
     */
    ResultObject<Map<String,String>> logout();

    /**
     * 用户注册
     * @return
     */
    ResultObject<Map<String,String>> register(LoginReq loginReq);
    
}
