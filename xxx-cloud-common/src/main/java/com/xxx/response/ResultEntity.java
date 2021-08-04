package com.xxx.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author ：jiweili
 * @date ：Created in 2021/8/4 14:25
 * @description：
 * @modified By：
 * @version: $
 */
@Data
@Builder
public class ResultEntity<T> {

    private int code;

    private String message;

    private T data;
}
