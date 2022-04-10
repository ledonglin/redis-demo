package com.example.jdcart.controller;

import com.example.jdcart.entity.Cart;
import com.example.jdcart.entity.CartPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 购物车行为
 *
 * @author llin
 * @date 2022/4/9
 **/
@Slf4j
@RestController
public class UserCartController {
    private static final String CART_KEY = "cart:user:";

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/addCart", method = RequestMethod.POST)
    public void addCart(HttpServletRequest request, Cart obj) {
        String key = CART_KEY + obj.getUserId();
        Boolean hasKey = redisTemplate.opsForHash().getOperations().hasKey(key);
        this.redisTemplate.opsForHash().put(key, obj.getProductId(), obj.getTotal());
        if (!hasKey) {
            redisTemplate.expire(key, 7, TimeUnit.DAYS);
        }
    }

    /**
     * 修改购物车的数量
     *
     * @param obj
     */
    @RequestMapping(value = "/updateCart", method = RequestMethod.POST)
    public void updateCart(Cart obj) {
        String key = CART_KEY + obj.getUserId();
        redisTemplate.opsForHash().put(key, obj.getProductId(), obj.getTotal());
    }


    @RequestMapping(value = "/deleteCart", method = RequestMethod.POST)
    public void deleteCart(Cart obj) {
        String key = CART_KEY + obj.getUserId();
        redisTemplate.opsForHash().delete(key, obj.getProductId());

    }

    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public CartPage findAll(String userId) {

        String key = CART_KEY + userId;
        CartPage cartPage = new CartPage();
        List<Cart> list = new ArrayList<>();

        long size = redisTemplate.opsForHash().size(key);
        cartPage.setTotal((int) size);
        cartPage.setCartList(list);

        Map<String, Integer> entries = redisTemplate.opsForHash().entries(key);

        entries.forEach((x, y) -> {
            Cart cart = new Cart();
            cart.setProductId(x);
            cart.setUserId(userId);
            cart.setTotal(y);
            list.add(cart);
        });

        return cartPage;
    }

}


