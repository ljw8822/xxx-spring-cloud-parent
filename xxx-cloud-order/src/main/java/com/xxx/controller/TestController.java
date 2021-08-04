package com.xxx.controller;

import com.xxx.entity.Order;
import com.xxx.feign.service.OrderStckService;
import com.xxx.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Api
@RestController
@Validated
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OrderStckService orderStckService;

    @Autowired
    OrderService orderService;

    @ApiOperation(value = "测试接口文档地址")
    @RequestMapping("get/{orderId}")
    public String getOrder(@ApiParam(value = "订单id") @PathVariable("orderId") String orderId) {
        return String.format("取得订单[%s]信息", orderId);
    }

    @RequestMapping("reduce-stock")
    public String reduceStock() {
        return orderStckService.reduceStock();
    }

    @RequestMapping("create-order")
    public String addOrder() {
        String result = restTemplate.getForObject("http://xxx-cloud-stock/stock/reduce", String.class);
        return result;
    }

    @GetMapping("get-order-list")
    public String getOrderList(@Valid @NotEmpty(message = "id不能为空") @RequestParam("id") String id) {
        for (Order order : orderService.queryOrderListByUserId("123")) {
            System.out.println(order);
        }
        return "getOrderList ok";
    }
}
