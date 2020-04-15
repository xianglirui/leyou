package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopInfo {
    private String areaName;
    private int star;
    private String site;
    private String tel;
    private String date;
    private String time;
    private int nums;
    private String price;
    private int ticket;
    private int adult;
    private int kid;
    private String desc;
    private String allPrice;
    private String photo;
    private String region;
}
