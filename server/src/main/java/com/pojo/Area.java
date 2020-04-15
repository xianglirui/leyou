package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    private int id;
    private int uid;
    private String name;
    private String site;
    private String tel;
    private String opentime;
    private String closetime;
    private String date;
    private String price;
    private int nums;
    //存储图片的本地路径
    private String photo;
    //景区公告
    private String descs;
    private int star;
    private String principal_tel;
    private String principal;
}
