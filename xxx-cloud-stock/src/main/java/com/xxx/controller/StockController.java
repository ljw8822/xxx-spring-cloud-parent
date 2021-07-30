package com.xxx.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
public class StockController {

    @RequestMapping("stock/reduce")
    public String reduceStock(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            System.out.println(request.getHeader(headerNames.nextElement()));
        }
        return "减库存";
    }
}
