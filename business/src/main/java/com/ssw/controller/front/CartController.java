package com.ssw.controller.front;

import com.ssw.common.ResponseCode;
import com.ssw.common.RoleEnum;
import com.ssw.common.ServerResponse;
import com.ssw.pojo.UserInfo;
import com.ssw.service.ICartService;
import com.ssw.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
@CrossOrigin
@RestController
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    ICartService cartService;
    //添加商品到购物车
    @RequestMapping("add")
    public ServerResponse addCart( Integer productId,
                                   Integer count,
                                  HttpSession session){
        UserInfo user=(UserInfo) session.getAttribute(Const.CURRENT_USER);

        return cartService.addProductCart(user.getId(),productId,count);

    }

    //购物车列表
    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session){

        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);
      /*  if(userInfo==null){
            return ServerResponse.serverResponseByError("需要登录");
        }*/


        return cartService.list(userInfo.getId());
    }
    //更新购物车某个产品数量
    @RequestMapping(value = "updatecount.do")
    public ServerResponse update(HttpSession session,Integer productId, Integer count){

        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);

        return cartService.updateCount(userInfo.getId(),productId,count);
    }
    //商品选中确认订单后,将商品的默认未选中的状态改为选中.
    @RequestMapping(value = "updatestatus.do")
    public ServerResponse updateStatus(HttpSession session,Integer productId, Integer checked){

        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);

        return cartService.updateStatus(userInfo.getId(),productId,checked);
    }
    //移除购物车某个产品
    @RequestMapping(value = "delete_product.do")
    public ServerResponse delete_product(HttpSession session,String productIds){

        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);

        return cartService.delete_product(userInfo.getId(),productIds);
    }
    //根据商品id,查询购物车中的商品
    @RequestMapping(value = "findcartproByid.do")
    public ServerResponse findProById(HttpSession session){

        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENT_USER);

        return cartService.findCartsByUseridAndChecked1(userInfo.getId());
    }

}
