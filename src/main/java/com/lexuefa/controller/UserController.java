package com.lexuefa.controller;

import com.lexuefa.comment.result.ResultObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Controller类
 * @author ukir
 * @date 2023/04/28 18:53
 **/
@RestController
public class UserController {
    
    
    
    public ResultObject<String> login(@Param("code") String code){
        return null;
    }
    
}
