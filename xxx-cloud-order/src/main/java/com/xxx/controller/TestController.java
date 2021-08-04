package com.xxx.controller;

import com.xxx.entity.Order;
import com.xxx.feign.service.OrderStckService;
import com.xxx.service.OrderService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OrderStckService orderStckService;

    @Autowired
    OrderService orderService;

    @RequestMapping("order/{orderId}")
    public String getOrder(@PathVariable("orderId") String orderId) {
        return String.format("取得订单[%]信息", orderId);
    }

    @RequestMapping("order/reduce-stock")
    public String reduceStock() {
        return orderStckService.reduceStock();
    }

    @RequestMapping("create-order")
    public String addOrder() {
        String result = restTemplate.getForObject("http://xxx-cloud-stock/stock/reduce", String.class);
        return result;
    }

    @GetMapping("get-order-list")
    public String getOrderList() {
        for (Order order : orderService.queryOrderListByUserId("123")) {
            System.out.println(order);
        }
        return "getOrderList ok";
    }
}
