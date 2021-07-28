package com.xxx.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @RequestMapping("stock/reduce")
    public String reduceStock() {
        return "减库存";
    }
}
