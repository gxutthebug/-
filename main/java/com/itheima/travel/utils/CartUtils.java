package com.itheima.travel.utils;


import com.itheima.travel.pojo.Cart;
import com.itheima.travel.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component // 组件 这个类会创建对象放到容器中, 相当于 cartUtils = new CartUtils();
public class CartUtils {

    @Autowired
    private RedisTemplate<String, Cart> redisTemplate;

    /**
     * 把购物车添加到Redis中
     * @param user 用户
     * @param cart 购物车
     */
    public void setCartToRedis(User user, Cart cart) {
        ValueOperations<String, Cart> ops = redisTemplate.opsForValue();
        ops.set("cart_" + user.getUsername(), cart);
    }

    /**
     * 从Redis中获取购物车对象
     * @param user 用户
     * @return 用户对应的购物车
     */
    public Cart getCartFromRedis(User user) {
        ValueOperations<String, Cart> ops = redisTemplate.opsForValue();
        // 1.从Redis中获取购物车对象
        Cart cart = ops.get("cart_" + user.getUsername());

        // 2.如果不存在购物车对象，就创建一个购物车
        if (cart == null) {
            cart = new Cart();
        }

        // 3.返回购物车对象
        return cart;
    }

    /**
     * 删除购物车
     * @param user 用户对象
     */
    public void removeCart(User user) {
        redisTemplate.delete("cart_" + user.getUsername());
    }
}
