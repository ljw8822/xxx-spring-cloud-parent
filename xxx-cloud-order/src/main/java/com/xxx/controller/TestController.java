package com.xxx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("order/{orderId}")
    public String getOrder(@PathVariable("orderId") String orderId) {
        return String.format("取得订单[%]信息", orderId);
    }

    @RequestMapping("create-order")
    public String addOrder() {
        String result = restTemplate.getForObject("http://xxx-cloud-stock/stock/reduce", String.class);
        return result;
    }
}
