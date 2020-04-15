package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayPool {
    private int id;
    private int uid;
    private int oid;
    private int aid;
    //从0-1是景区实体机器干的
    private int state;//0待使用  1已使用  -1申请退款  -2商家同意退款 -3已退款  -4失效
    private String times;
    private String money;
    private int toarea;
}
