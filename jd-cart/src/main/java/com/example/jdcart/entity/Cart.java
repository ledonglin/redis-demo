package com.example.jdcart.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author llin
 * @date 2022/4/9
 **/
@Data
@ApiModel("购物车")
public class Cart {
    private String userId;
    private String productId;
    private int total;
}
