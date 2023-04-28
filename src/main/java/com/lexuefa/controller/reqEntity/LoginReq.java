package com.lexuefa.controller.reqEntity;

import lombok.Data;

/**
 * 登录参数
 * @author ukir
 * @date 2023/04/28 19:01
 **/
@Data
public class LoginReq {
    private String userName;
    private String password;
    private String code;
    
}
