package com.xxx.config;

import cn.hutool.json.JSONException;
import com.xxx.response.ResultCode;
import com.xxx.response.ResultEntity;
import com.xxx.response.ResultWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：jiweili
 * @date ：Created in 2021/8/4 17:29
 * @description：
 * @modified By：
 * @version: $
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {



    /**
     * fastjson错误
     * @param e
     * @return
     */
    @ExceptionHandler(JSONException.class)
    @ResponseBody
    public ResultEntity<?> fastJSONExceptionHandler(JSONException e) {
        return ResultWrapper.fail("json格式错误");
    }

    /**
     * http参数错误
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResultEntity<?> httpMessageNotReadableExceptionHandler( HttpMessageNotReadableException e) {
        return ResultWrapper.fail("传入参数格式错误");
    }

    /**
     * 处理请求中 使用@Validated 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultEntity<?> bindExceptionHandler(HttpServletRequest request, BindException e) {
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return ResultWrapper.fail(message);
    }

    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResultEntity<?> constraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException e) {
        if (!e.getConstraintViolations().isEmpty()) {
            String message = e.getConstraintViolations().iterator().next().getMessage();
            return ResultWrapper.fail(message);
        }
        return ResultWrapper.success();
    }

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultEntity<?> methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResultWrapper.fail(message);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResultEntity<?> methodArgumentTypeMismatchExceptionHandler(HttpServletRequest request, MethodArgumentTypeMismatchException e) {
        return ResultWrapper.fail("传入参数类型不匹配");
    }
    /**
     * 全局异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultEntity<?> exceptionHandler(Exception e) {
        log.error("", e);
        return ResultWrapper.fail(ResultCode.ServerError,e.getMessage());
    }

    private ResultEntity<?> pramError(List<ObjectError> allErrors) {
        List<String> message = new ArrayList<>();
        allErrors.forEach(objectError -> {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                message.add("[" + fieldError.getField() + ":" + fieldError.getDefaultMessage() + "]");
            } else {
                message.add("[" + objectError.getObjectName() + ":" + objectError.getDefaultMessage() + "]");
            }
        });
        return ResultWrapper.fail(ResultCode.ParamUnValidated, String.join(",", message));
    }

}
