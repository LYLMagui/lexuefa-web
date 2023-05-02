package com.lexuefa.controller;

import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.lexuefa.comment.exception.ApiException;
import com.lexuefa.comment.result.ResultObject;
import com.lexuefa.controller.reqEntity.LoginReq;
import com.lexuefa.service.UserService;
import com.lexuefa.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
    private JwtTokenUtil jwtTokenUtil;


    /**
     * 用户登录
     * @param loginReq
     * @return
     */
    @PostMapping("/login")
    public ResultObject<Map<String,Object>> login(@RequestBody LoginReq loginReq){
        Integer id = userService.login(loginReq);
        String token = null;
        HashMap<String,Object> resultMap = new HashMap<>();
        if(id != null){
            token = jwtTokenUtil.generateToken(id);
            resultMap.put("code",200);
            resultMap.put("token",token);
            resultMap.put("message","登录成功");
        }else {
            throw new ApiException("账号或密码错误");
        }
        log.info("token:{}",token);
        
        //TODO
        return ResultObject.success(resultMap);
    }

    /**
     * 账号注册
     * @param loginReq
     * @return
     */
    @PostMapping("/register")
    public ResultObject<String> register(@RequestBody LoginReq loginReq){
        Integer registCount = userService.register(loginReq);

        if(registCount != null){
            return ResultObject.success("注册成功");
        }else {
            return ResultObject.failed("注册失败");
        }
    }
    
}
