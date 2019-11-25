package com.ssw.controller.front;

import com.ssw.common.ResponseCode;
import com.ssw.common.ServerResponse;
import com.ssw.pojo.Shipping;
import com.ssw.pojo.UserInfo;
import com.ssw.service.IShippingService;
import com.ssw.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping/")
public class ShippingController {
    @Autowired
    IShippingService shippingService;
    @RequestMapping(value = "add.do")
    public ServerResponse add(Shipping shipping, HttpSession session){
        UserInfo user=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        shipping.setUserId(user.getId());
        return shippingService.add(shipping);
    }

    @RequestMapping(value = "findShppingByUserId.do")
    public ServerResponse findShippingByUserId( HttpSession session){
        UserInfo user=(UserInfo) session.getAttribute(Const.CURRENT_USER);

        return shippingService.findShippingByUserId(user.getId());
    }
    @RequestMapping(value = "findShppingByShippingId.do")
    public ServerResponse findShippingByShippingId(HttpSession session,Integer shippingid){

        return shippingService.findShippingById(shippingid);
    }
    @RequestMapping(value="deliver")
    public ServerResponse deliver(Long orderNo){

        return shippingService.deliver(orderNo) ;
    }
}
