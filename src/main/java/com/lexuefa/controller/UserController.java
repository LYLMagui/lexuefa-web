package com.lexuefa.controller;

import com.alibaba.fastjson.JSON;
import com.lexuefa.comment.exception.ApiException;
import com.lexuefa.comment.result.ResultObject;
import com.lexuefa.controller.reqEntity.LoginReq;
import com.lexuefa.service.LoginService;
import com.lexuefa.service.MyUserDetailsService;
import com.lexuefa.service.UserService;
import com.lexuefa.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * User Controller类
 * @author ukir
 * @date 2023/04/28 18:53
 **/
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Resource
    private UserService userService;
    @Resource
    private LoginService loginService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    
    //redis缓存过期时间
    @Value("${Redis.cacheExpire}")
    private int cacheExpire;


    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 用户登录
     * @param loginReq
     * @return
     */
    @PostMapping("/login")
    public ResultObject<Map<String,String>> login(@RequestBody LoginReq loginReq){
        String reqJson = JSON.toJSONString(loginReq);
        //TODO
        log.info("login:{}", reqJson);
        return loginService.login(reqJson);
    }

    /**
     * 账号注册
     * @param loginReq
     * @return
     */
    @PostMapping("/register")
    public ResultObject<Map<String,String>> register(@RequestBody LoginReq loginReq){
        return  loginService.register(loginReq);
    }
    
    @PostMapping("/reflashToken")
    public ResultObject<String> reflashToken(HttpServletRequest request){
        String token = request.getHeader("token");
        Boolean exepired = jwtTokenUtil.isExepired(token);
        
        if(exepired){
            String refreshToken = jwtTokenUtil.refreshToken(token);
            Integer userId = jwtTokenUtil.getUserIdFromToken(token);
            redisTemplate.opsForValue().set("login:" + userId,token,cacheExpire,TimeUnit.HOURS);
            return ResultObject.success("token刷新成功",refreshToken);
        }else {
            return ResultObject.success("token未过期",null);
        }
    }
    
    @GetMapping("/logout")
    public ResultObject<Map<String,String>> logout(){
        return loginService.logout();
                
    }
}
