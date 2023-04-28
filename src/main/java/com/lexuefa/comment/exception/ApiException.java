package com.lexuefa.comment.exception;

import com.lexuefa.comment.result.IErrorCode;

/**
 * API异常处理
 * @author ukir
 * @date 2023/04/28 19:10
 **/
public class ApiException extends RuntimeException{
    
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    
     public ApiException(String message){
        super(message);
     }
     
     public ApiException(Throwable cause){
        super(cause);
     }
     
     public ApiException(String message, Throwable cause){
        super(message, cause);
     }

     public IErrorCode getErrorCode(){
        return errorCode;
     }
}
