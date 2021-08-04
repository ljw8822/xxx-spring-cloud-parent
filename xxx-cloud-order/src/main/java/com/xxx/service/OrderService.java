package com.xxx.service;

import com.google.common.collect.Lists;
import com.xxx.entity.Order;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：jiweili
 * @date ：Created in 2021/8/3 14:52
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class OrderService {


    @Cacheable( value = "TEST_CACHE")
    public List<Order> queryOrderListByUserId(String userId) {
        System.out.println("loading data from database...................");
        Order no0000001 = Order.builder().orderFee(12300).orderNo("NO0000001").build();
        Order no0000002 = Order.builder().orderFee(100000).orderNo("NO0000002").build();
        return Lists.newArrayList(no0000001, no0000002);
    }
}
