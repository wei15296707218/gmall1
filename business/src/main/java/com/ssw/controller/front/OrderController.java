package com.ssw.controller.front;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.ssw.common.ResponseCode;
import com.ssw.common.ServerResponse;
import com.ssw.pojo.UserInfo;
import com.ssw.service.IOrderService;
import com.ssw.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;


@RestController
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    IOrderService orderService;
//    /**
//     * 创建订单
//     * */
   @RequestMapping("{shippingid}")
    public ServerResponse createOrder(HttpSession session, @PathVariable("shippingid") Integer shippingId){

        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"需要登录");
        }
        return  orderService.createOrder(userInfo.getId(),shippingId);
   }
//
//
//    /**
//     * .取消订单
//     * */
//    @RequestMapping(value = "/cancel.do")
//    public ServerResponse cancel(HttpSession session,Long orderNo){
//
//        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
//        if(userInfo==null){
//            return ServerResponse.serverResponseByError("需要登录");
//        }
//        return  orderService.cancel(userInfo.getId(),orderNo);
//    }
//
//    /**
//     * .获取订单的商品信息
//     * */
//    @RequestMapping(value = "/get_order_cart_product.do.do")
//    public ServerResponse get_order_cart_product(HttpSession session){
//
//        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
//        if(userInfo==null){
//            return ServerResponse.serverResponseByError("需要登录");
//        }
//        return  orderService.get_order_cart_product(userInfo.getId());
//    }
//
//    /**
//     * 订单List
//     * */
//    @RequestMapping(value = "/list.do")
//    public ServerResponse list(HttpSession session,
//                               @RequestParam(required = false,defaultValue = "1")Integer pageNum,
//                               @RequestParam(required = false,defaultValue = "10")Integer pageSize){
//
//        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
//        if(userInfo==null){
//            return ServerResponse.serverResponseByError("需要登录");
//        }
//        return  orderService.list(userInfo.getId(),pageNum,pageSize);
//    }
//
//    /**
//     * 订单详情detail
//     * */
//    @RequestMapping(value = "/detail.do")
//    public ServerResponse detail(HttpSession session,Long orderNo){
//
//        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
//        if(userInfo==null){
//            return ServerResponse.serverResponseByError("需要登录");
//        }
//        return  orderService.detail(orderNo);
//    }
//
    /**
     * 支付接口
     * */
    @RequestMapping("pay/{orderNo}")
    public ServerResponse pay(HttpSession session,@PathVariable("orderNo") Long orderNo){

        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if(userInfo==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"需要登录");
        }

        return orderService.pay(userInfo.getId(),orderNo);
    }

    /**
     * 支付宝服务器回调应用服务器接口
     * */

    @RequestMapping(value = "/callback.do")
    public  String callback(HttpServletRequest request){

        System.out.println("======支付宝服务器回调应用服务器接口====");

        Map<String,String[]> params=request.getParameterMap();
        Map<String,String> requestparams= Maps.newHashMap();
        Iterator<String> it=params.keySet().iterator();
        while(it.hasNext()){
            String key=it.next();
            String[] strArr=params.get(key);
            String value="";
            for(int i=0;i<strArr.length;i++){
                value= (i==strArr.length-1)?value+strArr[i]: value+strArr[i]+",";
            }
            requestparams.put(key,value);

        }

        //setp1:支付宝验签

        try {
            requestparams.remove("sign_type");
            boolean result= AlipaySignature.rsaCheckV2(requestparams, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if(!result){
                return ("非法请求，验证不通过");
            }
            System.out.println("通过");
                orderService.alipay_callback(requestparams);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //处理业务逻辑
        return orderService.alipay_callback(requestparams);

    }
//
//
//    /**
//     * 查询订单的支付状态
//     * */
//    @RequestMapping(value = "/query_order_pay_status.do")
//    public ServerResponse query_order_pay_status(HttpSession session,Long orderNo){
//
//        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
//        if(userInfo==null){
//            return ServerResponse.serverResponseByError("需要登录");
//        }
//
//        return orderService.query_order_pay_status(orderNo);
//    }
}
