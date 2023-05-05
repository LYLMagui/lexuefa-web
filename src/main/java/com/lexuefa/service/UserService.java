package com.lexuefa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lexuefa.controller.reqEntity.LoginReq;
import com.lexuefa.entity.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface UserService extends IService<User> {

    /**
     * 获取用户信息
     * @param loginReq
     * @return
     */
    User selectOne(LoginReq loginReq);
}
