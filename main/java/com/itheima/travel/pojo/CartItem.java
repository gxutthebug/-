package com.itheima.travel.pojo;

import lombok.Data;

// 购物车项
@Data // 生成get/set/toString/hashCode
public class CartItem {
    private Route route; // 线路对象
    private int num; // 用户购买的商品数量
    private double subTotal; // 小计 = 数量 * 单价

    // 计算小计 = 数量 * 单价
    public double getSubTotal() {
        subTotal = num * route.getPrice();
        return subTotal;
    }
}
