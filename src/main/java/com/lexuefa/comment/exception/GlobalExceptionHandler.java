package com.lexuefa.comment.exception;

import com.lexuefa.comment.result.ResultObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * @author ukir
 * @date 2023/04/28 22:36
 **/
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public ResultObject handle(ApiException e){
        if(e.getErrorCode() != null){
            return ResultObject.failed(e.getErrorCode());
        }
        return ResultObject.failed(e.getMessage());
    }
}
