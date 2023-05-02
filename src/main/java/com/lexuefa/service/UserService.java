package com.lexuefa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lexuefa.controller.reqEntity.LoginReq;
import com.lexuefa.entity.user.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService extends IService<User> {

    /**
     * 获取用户信息
     * @param loginReq
     * @return
     */
    User selectOne(LoginReq loginReq);

    /**
     * 用户登录
     * @param loginReq
     * @return
     */
    Integer login(LoginReq loginReq);

    /**
     * 账号注册
     * @param loginReq
     * @return
     */
    Integer register(LoginReq loginReq);
    
}
