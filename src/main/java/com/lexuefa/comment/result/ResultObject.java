package com.lexuefa.comment.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回对象
 * @author ukir
 * @date 2023/04/18 19:38
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultObject<T> {

    private int code;
    private String message;
    private T data;

    /**
     * 成功返回结果
     * 
     * @param data 获取的数据
     * @return
     * @param <T>
     */
    public static <T> ResultObject<T> success(T data){
        return new ResultObject<T>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(),data);
    }

    /**
     * 成功返回结果
     * @param data 获取的数据 
     * @param message 返回信息
     * @return
     * @param <T>
     */
    public static <T> ResultObject<T> success(String message,T data){
        return new ResultObject<T>(ResultCode.SUCCESS.getCode(),message,data);
    }

    /**
     * 失败的返回结果
     * @param errorCode 错误码
     * @return 
     * @param <T>
     */
    public static <T> ResultObject<T> failed(IErrorCode errorCode){
        return new ResultObject<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果 
     * @param errorCode 错误码
     * @param message 错误信息
     * @return
     * @param <T>
     */
    public static <T> ResultObject<T> failed(IErrorCode errorCode,String message){
        return new ResultObject<>(errorCode.getCode(), message, null);
    }

    /**
     * 失败的返回结果
     * @param message 提示信息
     * @return
     * @param <T>
     */
    public static <T> ResultObject<T> failed(String message){
        return new ResultObject<>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @return
     * @param <T>
     */
    public static <T> ResultObject<T> failed(){
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> ResultObject<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> ResultObject<T> validateFailed(String message) {
        return new ResultObject<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }
    /**
     * 未登录返回结果
     */
    public static <T> ResultObject<T> unauthorized(T data) {
        return new ResultObject<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }
    /**
     * 未授权返回结果
     */
    public static <T> ResultObject<T> forbidden(T data) {
        return new ResultObject<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }
    
    
    
}
