package com.xxx.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author ：jiweili
 * @date ：Created in 2021/8/3 14:53
 * @description：
 * @modified By：
 * @version: $
 */
@Data
@Accessors
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String orderNo;
    private long orderFee;
}
