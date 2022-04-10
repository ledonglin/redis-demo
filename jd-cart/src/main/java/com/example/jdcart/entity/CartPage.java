package com.example.jdcart.entity;

import lombok.Data;

import java.util.List;

/**
 * @author llin
 * @date 2022/4/9
 **/
@Data
public class CartPage {
    int total;
    List<Cart> cartList;
}
