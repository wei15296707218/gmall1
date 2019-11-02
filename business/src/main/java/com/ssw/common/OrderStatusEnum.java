package com.ssw.common;

/**
 * @Auther:Wss
 * @Date:2019/10/30 0030
 * @Description:com.ssw.common
 * @version: 1.0
 */
public enum OrderStatusEnum {

    ORDER_CANCEL(0,"已取消"),
    ORDER_NO_PAY(1,"未付款"),
    ORDER_PAYED(2,"已付款"),
    ORDER_SEND(3,"已发货"),
    ORDER_SUCCESS(4,"交易成功"),
    ORDER_CLOSED(5,"交易关闭");


    private int status;
    private String desc;

    OrderStatusEnum() {
    }
    OrderStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static OrderStatusEnum codeof(Integer status){
        for(OrderStatusEnum orderStatusEnum:values()){
            if (status==orderStatusEnum.getStatus()){
                return orderStatusEnum;
            }
        }
        return null;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
