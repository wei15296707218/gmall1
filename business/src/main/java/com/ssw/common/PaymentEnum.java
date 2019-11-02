package com.ssw.common;

/**
 * @Auther:Wss
 * @Date:2019/10/30 0030
 * @Description:com.ssw.common
 * @version: 1.0
 */
public enum PaymentEnum {
    PAYMENT_ONLINE(1,"在线支付"),
    PAYMENT_OFFLINE(2,"货到付款");

    private  int  code;
    private String desc;
    private PaymentEnum(int code,String desc){
        this.code=code;
        this.desc=desc;
    }
    public static PaymentEnum codeof(Integer status){
        for(PaymentEnum paymentEnum:values()){
            if (status==paymentEnum.getCode()){
                return paymentEnum;
            }
        }
        return null;
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
}
