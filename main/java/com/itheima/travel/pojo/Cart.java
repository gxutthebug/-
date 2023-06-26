package com.itheima.travel.pojo;

import lombok.Data;

import java.util.Collection;
import java.util.LinkedHashMap;

// 购物车对象
@Data
public class Cart {
    private int cartNum; // 购物车中商品总数量
    private double cartTotal; // 商品总价格
    private LinkedHashMap<String, CartItem> cartItemMap = new LinkedHashMap<>(); // 必须先创建好Map,后期才能往Map中添加数据.键是线路的id, 值是CartItem

    // 计算购物车中商品总数量 = 所有CartItem中的数量总和
    public int getCartNum() {
        cartNum = 0;

        // 获Map取所有的值
        Collection<CartItem> values = cartItemMap.values();
        for (CartItem cartItem : values) {
            // 把所有的数量相加
            cartNum += cartItem.getNum();
        }

        return cartNum;
    }

    // 计算商品总价格 = 所有CartItem中的小计总和
    public double getCartTotal() {
        cartTotal = 0;

        // 获取Map所有的值
        Collection<CartItem> values = cartItemMap.values();
        for (CartItem cartItem : values) {
            // 把所有的小计相加
            cartTotal += cartItem.getSubTotal();
        }

        return cartTotal;
    }
}
