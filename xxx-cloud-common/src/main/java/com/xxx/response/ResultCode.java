package com.xxx.response;

/**
 * @author ：jiweili
 * @date ：Created in 2021/8/4 14:32
 * @description：
 * @modified By：
 * @version: $
 */

public enum ResultCode {

    SUCCESS(200,"SUCCESS"),
    ServerError(500, "系统错误"),
    ParamUnValidated(501, "请求参数校验失败");

    ResultCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    int code;

    String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
