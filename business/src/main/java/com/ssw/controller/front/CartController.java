package com.ssw.controller.front;

import com.ssw.common.ResponseCode;
import com.ssw.common.RoleEnum;
import com.ssw.common.ServerResponse;
import com.ssw.pojo.UserInfo;
import com.ssw.service.ICartService;
import com.ssw.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    ICartService cartService;
    //添加商品到购物车
    @RequestMapping("add/{productId}/{count}")
    public ServerResponse addCart(@PathVariable("productId") Integer productId,
                                  @PathVariable("count") Integer count,
                                  HttpSession session){
        UserInfo user=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        return cartService.addProductCart(user.getId(),productId,count);

    }
}
