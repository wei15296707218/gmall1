package com.ssw.service.impl;

import com.ssw.common.CheckEnum;
import com.ssw.common.ResponseCode;
import com.ssw.common.ServerResponse;
import com.ssw.dao.CartMapper;
import com.ssw.pojo.Cart;
import com.ssw.pojo.Product;
import com.ssw.service.ICartService;
import com.ssw.service.IProductService;
import com.ssw.utils.BigDecimalUtils;
import com.ssw.vo.CartProductVO;
import com.ssw.vo.CartVO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {



    @Autowired
    IProductService productService;
    @Autowired
    CartMapper cartMapper;
    @Override
    public ServerResponse addProductCart(Integer userId, Integer productId, Integer count) {
        //1.参数非空判断
        if (productId==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品id不能为空");
        }
        if (count==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品数量不能为0");
        }
        //2.根据商品Id判断商品是否存在（库存）
        ServerResponse<Product> serverResponse=productService.findProductById(productId);
        if (!serverResponse.isSuccess()){//商品不存在
            return ServerResponse.createServerResponseByError(serverResponse.getStatus(),serverResponse.getMsg());
        }else{
            Product product=serverResponse.getData();
            if (product.getStock()<=0){
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"商品已售空");
            }
        }
        //3.判断商品是否在购物车
        Cart cart=cartMapper.findCartByuserIdAndProductId(userId,productId);
        if (cart==null){
            //添加
            Cart newCart=new Cart();
            newCart.setUserId(userId);
            newCart.setProductId(productId);
            newCart.setQuantity(count);
            newCart.setChecked(CheckEnum.CART_PRODUCT_UNCHECK.getCheck());
            int result=cartMapper.insert(newCart);
            if (result<=0){
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"添加失败");
            }
        }else{
            //更新商品在购物车中的数量
            cart.setQuantity(cart.getQuantity()+count);
            int result=cartMapper.updateByPrimaryKey(cart);
            if (result<=0){
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"更新失败");
            }
        }
        //4.封装购物车对象
        CartVO cartVO=getCartVO(userId);
        //5.返回CartVO
        return ServerResponse.createServerResponseBySuccess(cartVO);
    }



    @Override
    public ServerResponse cleanCart(List<Cart> cartList) {

        if (cartList==null || cartList.size()<=0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"购物车中的商品不能为空");
        }
      int result =   cartMapper.cleanCart(cartList);
        if (result!=cartList.size()){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"购物车中的商品清空失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }
/*
购物车列表信息
 */
    @Override
    public ServerResponse list(Integer userId) {
        CartVO cartVO= getCartVO(userId);
        return ServerResponse.createServerResponseBySuccess(cartVO);
    }

    @Override
    public ServerResponse updateStatus(Integer userId, Integer productId, Integer checked) {
        //step1:参数判定
        if(productId==null||checked==null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_NOT_NULL,"参数不能为空");
        }

        //step2:查询购物车中商品
        Cart cart= cartMapper.findCartByuserIdAndProductId(userId,productId);
        if(cart!=null){
            //step3:更新数量
            cart.setChecked(CheckEnum.CART_PRODUCT_CHECK.getCheck());
            cartMapper.updateByPrimaryKey(cart);
        }


        //step4:返回cartvo
        return ServerResponse.createServerResponseBySuccess(getCartVO(userId));
    }
    @Override
    public ServerResponse<List<Cart> > findCartsByUseridAndChecked1(Integer userId) {

        List<Cart> cartlist = cartMapper.findCartsByUseridAndChecked(userId);
        if (cartlist==null||cartlist.size()==0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"没有选中的商品");
        }else{
            for(Cart cart:cartlist){
               userId=cart.getUserId();
            }
        }
        return ServerResponse.createServerResponseBySuccess(getCartVO1(userId));
    }
    @Override
    public ServerResponse<List<Cart> > findCartsByUseridAndChecked(Integer userId) {

        List<Cart> cartlist = cartMapper.findCartsByUseridAndChecked(userId);
        if (cartlist==null||cartlist.size()==0){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"没有选中的商品");
        }
        return ServerResponse.createServerResponseBySuccess(cartlist);
    }
    @Override
    public ServerResponse updateCount(Integer userId, Integer productId, Integer count) {
        //step1:参数判定
        if(productId==null||count==null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_NOT_NULL,"参数不能为空");
        }

        //step2:查询购物车中商品
        Cart cart= cartMapper.findCartByuserIdAndProductId(userId,productId);
        if(cart!=null){
            //step3:更新数量
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);
        }


        //step4:返回cartvo
        return ServerResponse.createServerResponseBySuccess(getCartVO(userId));
    }

    @Override
    public ServerResponse delete_product(Integer userId, String productIds) {
        //step1:参数非空校验
        if(productIds==null||productIds.equals("")){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_NOT_NULL,"参数不能为空");
        }
        //step2:productIds-->List<Integer>
        List<Integer> productIdList=Lists.newArrayList();
        String[] productIdsArr=  productIds.split(",");
        if(productIdsArr!=null&&productIdsArr.length>0){
            for(String productIdstr:productIdsArr){
                Integer productId=Integer.parseInt(productIdstr);
                productIdList.add(productId);
            }
        }

        //step3:调用dao
        cartMapper.deleteByUseridAndProductIds(userId,productIdList);
        //step4:返回结果
        return ServerResponse.createServerResponseBySuccess(getCartVO(userId));
    }

    private CartVO getCartVO1(Integer userId){
        CartVO cartVO=new CartVO();
        //1.根据userid查询该用户的购物信息  List<Cart>
        //List<Cart> cartList=cartMapper.findCartsByUserid(userId);
        List<Cart> cartList = cartMapper.findCartsByUseridAndChecked(userId);
        if (cartList==null||cartList.size()==0){
            return cartVO;
        }
        //定义购物车商品总价格
        BigDecimal cartTotalPrice=new BigDecimal("0");
        //2.List<Cart>-->List<CartProductVO>
        List<CartProductVO> cartProductVOList= Lists.newArrayList();
        int limi_quantity=0;
        String limitQuantity=null;
        for (Cart cart:cartList){
            //Cart-->CartProduct
            CartProductVO cartProductVO=new CartProductVO();
            cartProductVO.setId(cart.getId());
            cartProductVO.setUserId(cart.getUserId());
            cartProductVO.setProductId(cart.getProductId());

            ServerResponse<Product> serverResponse=productService.findProductById(cart.getProductId());
            if (serverResponse.isSuccess()){
                Product product= (Product)serverResponse.getData();
                if (product.getStock()>=cart.getQuantity()){
                    limi_quantity=cart.getQuantity();
                    limitQuantity="LIMIT_NUM_SUCCESS";
                }else{
                    limi_quantity=product.getStock();
                    limitQuantity="LIMIT_NUM_FAIL";
                }
                cartProductVO.setQuantity(limi_quantity);
                cartProductVO.setLimitQuantity(limitQuantity);
                cartProductVO.setProductName(product.getName());
                cartProductVO.setProductSubtitle(product.getSubtitle());
                cartProductVO.setProductMainImage(product.getMainImage());
                cartProductVO.setProductPrice(product.getPrice());
                cartProductVO.setProductStatus(product.getStatus());
                cartProductVO.setProductTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(),cart.getQuantity()*1.0));
                cartProductVO.setProductStock(product.getStock());
                cartProductVO.setProductChecked(cart.getChecked());
                cartProductVOList.add(cartProductVO);
                if (cart.getChecked()==CheckEnum.CART_PRODUCT_CHECK.getCheck()){
                    //商品被选中
                    cartTotalPrice= BigDecimalUtils.add(cartTotalPrice.doubleValue(),cartProductVO.getProductTotalPrice().doubleValue());
                }
            }
        }
        cartVO.setCartProductVOList(cartProductVOList);
        //3.计算总的价格
        cartVO.setCarttotalprice(cartTotalPrice);

        //4.判断是否全选
        Integer isAllChecked=cartMapper.isAllChecked(userId);
        if (isAllChecked==0){
            //全选
            cartVO.setIsallchecked(true);
        }else{
            cartVO.setIsallchecked(false);
        }

        //5.构建cartvo
        return cartVO;

    }

    private CartVO getCartVO(Integer userId){
        CartVO cartVO=new CartVO();
        //1.根据userid查询该用户的购物信息  List<Cart>
        List<Cart> cartList=cartMapper.findCartsByUserid(userId);
        if (cartList==null||cartList.size()==0){
            return cartVO;
        }
        //定义购物车商品总价格
        BigDecimal cartTotalPrice=new BigDecimal("0");
        //2.List<Cart>-->List<CartProductVO>
        List<CartProductVO> cartProductVOList= Lists.newArrayList();
        int limi_quantity=0;
        String limitQuantity=null;
        for (Cart cart:cartList){
            //Cart-->CartProduct
            CartProductVO cartProductVO=new CartProductVO();
            cartProductVO.setId(cart.getId());
            cartProductVO.setUserId(cart.getUserId());
            cartProductVO.setProductId(cart.getProductId());

            ServerResponse<Product> serverResponse=productService.findProductById(cart.getProductId());
            if (serverResponse.isSuccess()){
                Product product= (Product)serverResponse.getData();
                if (product.getStock()>=cart.getQuantity()){
                    limi_quantity=cart.getQuantity();
                    limitQuantity="LIMIT_NUM_SUCCESS";
                }else{
                    limi_quantity=product.getStock();
                    limitQuantity="LIMIT_NUM_FAIL";
                }
                cartProductVO.setQuantity(limi_quantity);
                cartProductVO.setLimitQuantity(limitQuantity);
                cartProductVO.setProductName(product.getName());
                cartProductVO.setProductSubtitle(product.getSubtitle());
                cartProductVO.setProductMainImage(product.getMainImage());
                cartProductVO.setProductPrice(product.getPrice());
                cartProductVO.setProductStatus(product.getStatus());
                cartProductVO.setProductTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(),cart.getQuantity()*1.0));
                cartProductVO.setProductStock(product.getStock());
                cartProductVO.setProductChecked(cart.getChecked());
                cartProductVOList.add(cartProductVO);
                if (cart.getChecked()==CheckEnum.CART_PRODUCT_CHECK.getCheck()){
                    //商品被选中
                    cartTotalPrice= BigDecimalUtils.add(cartTotalPrice.doubleValue(),cartProductVO.getProductTotalPrice().doubleValue());
                }
            }
        }
        cartVO.setCartProductVOList(cartProductVOList);
        //3.计算总的价格
         cartVO.setCarttotalprice(cartTotalPrice);

        //4.判断是否全选
        Integer isAllChecked=cartMapper.isAllChecked(userId);
        if (isAllChecked==0){
            //全选
            cartVO.setIsallchecked(true);
        }else{
            cartVO.setIsallchecked(false);
        }

        //5.构建cartvo
        return cartVO;

    }

}
