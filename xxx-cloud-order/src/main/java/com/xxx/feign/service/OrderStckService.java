package com.xxx.feign.service;

import com.xxx.feign.interceptor.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ：jiweili
 * @date ：Created in 2021/7/30 16:50
 * @description：
 * @modified By：
 * @version: $
 */
@FeignClient(value = "xxx-cloud-stock",configuration = FeignConfiguration.class)
public interface OrderStckService {

    @GetMapping("stock/reduce")
    String reduceStock();
}
