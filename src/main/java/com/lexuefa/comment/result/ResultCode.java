package com.lexuefa.comment.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回结果状态码
 * @author ukir
 * @date 2023/04/18 19:55
 **/
public enum ResultCode implements IErrorCode{
    SUCCESS(200,"操作成功"),
    FAILED(500,"操作失败"),
    VALIDATE_FAILED(506,"参数校验失败"),
    UNAUTHORIZED(401,"暂未登录或token已过期"),
    FORBIDDEN(403,"没有相关权限");
    private int code;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
