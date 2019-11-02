package com.ssw.service;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.Cart;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ICartService {
    //添加商品购物车
    public ServerResponse addProductCart(Integer userId,Integer productId, Integer count);
    //查看用户选中的商品
    ServerResponse<List<Cart>> findCartsByUseridAndChecked(@Param("userId") Integer userId);
    //批量删除购物车中的信息
    ServerResponse cleanCart(List<Cart> cartList);
}
