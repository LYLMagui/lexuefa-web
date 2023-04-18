package com.lexuefa.comment.exception;

import com.lexuefa.comment.result.IErrorCode;

/**
 * 自定义接口异常
 * @author ukir
 * @date 2023/04/18 20:50
 **/
public class ApiException extends RuntimeException{
    private IErrorCode errorCode;
    
    public ApiException(IErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        
    }
}
