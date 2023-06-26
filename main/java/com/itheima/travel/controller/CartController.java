package com.itheima.travel.controller;

import com.itheima.travel.entity.Result;
import com.itheima.travel.pojo.*;
import com.itheima.travel.service.RouteService;
import com.itheima.travel.utils.CartUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
@RestController
@RequestMapping(path = "/cart", produces = "application/json;charset=utf-8")
public class CartController {
    @Autowired
    private CartUtils cartUtils;
    @Autowired
    private RouteService routeService;

    @RequestMapping("/addCart")
    public Result addCart(Integer num, String rid, HttpSession session) {
        //从会话域中获取用户对象
        User user = (User) session.getAttribute("user");
        //从Redis中获取购物车对象
        Cart cart = cartUtils.getCartFromRedis(user);
        //获取所有的购物项
        LinkedHashMap<String, CartItem> cartItemMap = cart.getCartItemMap();
        //判断当前商品的购物车项是否存在
        CartItem cartItem = cartItemMap.get(rid);
        //如果为空就表示不存在
        if (cartItem == null) {
            cartItem = new CartItem();
            //查询route对象
            Route route = routeService.findRoute(Integer.parseInt(rid));
            //设置属性
            cartItem.setRoute(route);
            cartItem.setNum(num);
            //将这个购物车项放在cart中去
            cartItemMap.put(rid, cartItem);
        }
        //不为空，只要累加数量就行了
        else {
            cartItem.setNum(cartItem.getNum() + num);
        }
        //将"购物车"更新到redis中
        cartUtils.setCartToRedis(user, cart);
        //将购物车项添加到会话域中
        session.setAttribute("cartItem",cartItem);
        //返回ResultInfo对象
        return new Result(true, "添加到购物车成功");
    }

    @RequestMapping("/showCartItem")
    public Result showCartItem(HttpSession session) {
        CartItem cartItem = (CartItem) session.getAttribute("cartItem");
        return new Result(true, cartItem);
    }

    @RequestMapping("/findAll")
    public Result findAll(HttpSession session) {
        User user = (User) session.getAttribute("user");
        //从redis中获取cart对象
        Cart cart = cartUtils.getCartFromRedis(user);
        //封装到结果对象中
        return new Result(true, cart);
    }

    @RequestMapping("/deleteCartItem")
    public Cart deleteCartItem(String rid, HttpSession session) {
        //从会话中获取用户对象
        User user = (User) session.getAttribute("user");
        //从redis中获取cart对象
        Cart cart = cartUtils.getCartFromRedis(user);
        //得到所有的购物车项
        LinkedHashMap<String, CartItem> cartItemMap = cart.getCartItemMap();
        cartItemMap.remove(rid);
        //更新cart到redis中
        cartUtils.setCartToRedis(user, cart);
        //返回cart对象
        return cart;
    }

}
