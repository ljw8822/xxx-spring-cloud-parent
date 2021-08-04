package com.xxx.config;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：jiweili
 * @date ：Created in 2021/8/4 17:54
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
public class ErrorController {

    @RequestMapping("/page/error1")
    public String getErrorPath(Throwable throwable) {
        return null;
    }

}
