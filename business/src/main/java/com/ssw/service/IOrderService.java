package com.ssw.service;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.Cart;
import com.sun.javafx.css.StyleManager;

import java.util.List;
import java.util.Map;

public interface IOrderService {


    /**
     * 创建订单
     * */
    ServerResponse createOrder(Integer userId, Integer shippingId);

    /**
     * 取消订单
     *
     */
    //ServerResponse cancel(Integer userId, Long orderNo);
    /**
     * 根据用户id查看用户的已选中的商品
     * */
   // ServerResponse get_order_cart_product(Integer userId);
    /**
     * 订单列表
     * */
    //ServerResponse list(Integer userId, Integer pageNum, Integer pageSize);
    /**
     * 订单详情
     * */
    //ServerResponse detail(Long orderNo);
    /**
     *
     * 支付接口
     * */
    ServerResponse pay(Integer userId, Long orderNo);

    /**
     *
     * 支付宝回调接口
     * */
   String alipay_callback(Map<String, String> requestParams);
    /**
     *
     * 查询订单的支付状态
     * */
    //ServerResponse query_order_pay_status(Long orderNo);


    /**
     * 根据创建时间查询订单
     * */
   // void closeOrder(String time);

    /*

     */
    public  ServerResponse getCartOrderItem(Integer userId,List<Cart> cartlist);
}