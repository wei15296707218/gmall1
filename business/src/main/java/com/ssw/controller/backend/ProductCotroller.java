package com.ssw.controller.backend;

import com.ssw.common.ResponseCode;
import com.ssw.common.RoleEnum;
import com.ssw.common.ServerResponse;
import com.ssw.pojo.Product;
import com.ssw.pojo.UserInfo;
import com.ssw.service.IProductService;
import com.ssw.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
@CrossOrigin
@RestController
@RequestMapping(value = "/manage/product/")
public class ProductCotroller {
    @Autowired
    IProductService productService;
    //商品添加&更新
    @RequestMapping(value = "save.do")
    public ServerResponse addOrUpdate(Product product, HttpSession session){
        UserInfo user=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=user.getRole();
        if (role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"权限不足");
        }
        return productService.addOrUpdate(product);
    }
    //搜索商品
    @RequestMapping(value = "search.do")
    public ServerResponse search(@RequestParam(name="productName",required = false) String productName,
                                 @RequestParam(name="productId",required = false) Integer productId,
                                 @RequestParam(name="pageNum",required = false,defaultValue = "1") Integer pageNum,
                                 @RequestParam(name="pageSize",required = false,defaultValue = "10") Integer pageSize,HttpSession session){
       // UserInfo user=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        /*if (user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=user.getRole();
        if (role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"权限不足");
        }*/
        return productService.search(productName, productId, pageNum, pageSize);

    }
    @RequestMapping(value = "detail.do")
    public ServerResponse detail(Integer productId,HttpSession session){
        /*UserInfo user=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=user.getRole();
        if (role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"权限不足");
        }*/
        return productService.detail(productId);
    }
    //查看是否是热销产品
    @RequestMapping("hot.do")
    public ServerResponse isHotProduct(Integer category_id,Integer is_hot){

            return productService.isHotProduct(category_id,is_hot);

    }
}
