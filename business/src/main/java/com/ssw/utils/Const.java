package com.ssw.utils;

public class Const {
    public static final String CURRENT_USER="USER";


public  enum OrderStatusEnum{

    ORDER_CANCELED(0,"已取消"),
    ORDER_UN_PAY(10,"未付款"),
    ORDER_PAYED(20,"已付款"),
    ORDER_SEND(40,"已发货"),
    ORDER_SUCCESS(50,"交易成功"),
    ORDER_CLOSED(60,"交易关闭")
    ;
    private  int  code;
    private String desc;
    private OrderStatusEnum(int code,String desc){
        this.code=code;
        this.desc=desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public  static  OrderStatusEnum codeOf(Integer code){
        for(OrderStatusEnum orderStatusEnum: values()){
            if(code==orderStatusEnum.getCode()){
                return  orderStatusEnum;
            }
        }
        return  null;
    }


}
}