package com.lexuefa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lexuefa.dao.user.UserDao;
import com.lexuefa.entity.user.User;
import com.lexuefa.service.UserService;
import org.springframework.stereotype.Service;

/**
 * UserService实现类
 * @author ukir
 * @date 2023/04/28 22:56
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService{
    
}
