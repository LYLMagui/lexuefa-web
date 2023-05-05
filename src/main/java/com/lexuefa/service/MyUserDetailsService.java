package com.lexuefa.service;

import com.lexuefa.controller.reqEntity.LoginReq;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 继承UserDetailsService接口增加自定义方法
 * @author LiYl
 */
public interface MyUserDetailsService extends UserDetailsService {
    /**
     * 查询用户信息
     * @param loginReq
     * @return
     */
    UserDetails loadUserByOpenId(LoginReq loginReq);
}
