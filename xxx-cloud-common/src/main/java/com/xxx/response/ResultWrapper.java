package com.xxx.response;

/**
 * @author ：jiweili
 * @date ：Created in 2021/8/4 14:27
 * @description：
 * @modified By：
 * @version: $
 */
public class ResultWrapper<T> {

    public final static String SUCCESS = "success";

    public static <T> ResultEntity success(T data) {
        return ResultEntity.builder().code(200).message(SUCCESS).data(data).build();
    }
    public static <T> ResultEntity success() {
        return ResultEntity.builder().code(200).message(SUCCESS).build();
    }

    public static ResultEntity fail(String message) {
        return ResultEntity.builder().code(500).message(message).build();
    }

    public static ResultEntity fail(int code, String message) {
        return ResultEntity.builder().code(code).message(message).build();
    }

    public static ResultEntity fail(ResultCode code) {
        return ResultEntity.builder().message(code.getMessage()).code(code.getCode()).build();
    }
    public static ResultEntity fail(ResultCode code, String extraMessage) {
        return ResultEntity.builder().message(code.getMessage() + "-" + extraMessage).code(code.getCode()).build();
    }
}
