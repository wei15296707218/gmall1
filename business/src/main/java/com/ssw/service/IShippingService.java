package com.ssw.service;


import com.ssw.common.ServerResponse;
import com.ssw.pojo.Shipping;

public interface IShippingService {
    public ServerResponse add(Shipping shipping);

    public ServerResponse findShippingById(Integer shippingid);
}
