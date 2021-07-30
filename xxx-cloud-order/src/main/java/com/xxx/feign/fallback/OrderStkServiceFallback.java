package com.xxx.feign.fallback;

import com.xxx.feign.service.OrderStckService;

/**
 * @author ：jiweili
 * @date ：Created in 2021/7/30 16:59
 * @description：
 * @modified By：
 * @version: $
 */
public class OrderStkServiceFallback implements OrderStckService {
    @Override
    public String reduceStock() {
        return "订单调用服务出错";
    }
}
