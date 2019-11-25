package com.ssw.service.impl;

import com.ssw.common.ResponseCode;
import com.ssw.common.ServerResponse;
import com.ssw.dao.ShippingMapper;
import com.ssw.pojo.Shipping;
import com.ssw.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingServiceImpl implements IShippingService {
    @Autowired
    ShippingMapper shippingMapper;
    @Override
    public ServerResponse add(Shipping shipping) {
        //1.参数非空判断
        if (shipping==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"参数不能为空");
        }
        Integer shippingid=shipping.getId();
        if ((shippingid==null)){
            //添加
            int result=shippingMapper.insert(shipping);
            if (result<=0){
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"添加地址失败");
            }else{
                return ServerResponse.createServerResponseBySuccess(shipping.getId());
            }
        }else{
            //更新
            int result=shippingMapper.updateByPrimaryKey(shipping);
            if (result<=0){
                return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"更新收获地址失败");
            }else {
                return ServerResponse.createServerResponseBySuccess(null,"更新地址成功");
            }
        }

    }

    @Override
    public ServerResponse findShippingById(Integer shippingid) {
        if (shippingid==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"shippingid不能为空");
        }
       Shipping shipping =  shippingMapper.selectByPrimaryKey(shippingid);
        if (shipping==null){
            return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"收货地址不能为空");
        }
        return ServerResponse.createServerResponseBySuccess(shipping);

    }

    @Override
    public ServerResponse findShippingByUserId(Integer userid) {
       List<Shipping> shippings =  shippingMapper.findShippingByUserId(userid);
       if (shippings==null || shippings.size()==0){
           return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"还没有添加地址,请添加地址");
       }
        return ServerResponse.createServerResponseBySuccess(shippings);
    }

    @Override
    public ServerResponse deliver(Long orderNo) {

        int result = shippingMapper.deliver(orderNo);
        if (result!=1){
          return ServerResponse.createServerResponseByError(ResponseCode.ERROR,"发货失败");
        }
        return ServerResponse.createServerResponseBySuccess();
    }

}
