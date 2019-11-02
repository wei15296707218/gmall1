package com.ssw.common;

public enum ProductStatusEnum {
    PRODUCT_ONLINE(1,"在售"),
    PRODUCT_OFFLINE(2,"下架"),
    PRODUCT_DELETE(3,"删除")
            ;
    private  int  code;
    private String desc;
    private ProductStatusEnum(int code,String desc){
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
}