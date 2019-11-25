package com.ssw.service;


import com.ssw.common.ServerResponse;
import com.ssw.pojo.Shipping;

public interface IShippingService {
    public ServerResponse add(Shipping shipping);

    public ServerResponse findShippingById(Integer shippingid);

    public ServerResponse findShippingByUserId(Integer userid);
    //发货
    public ServerResponse deliver(Long oederNo);
}
