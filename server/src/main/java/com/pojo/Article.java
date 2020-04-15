package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private int id;
    private User user;
    private int uid;
    private int reprint;//是否禁止转载
    private String article;
    private String title;
    private String times;
    private int good;
    private int collect;
    private int dis;//是否显示，内容违规会被封杀，dis设置为1，即不显示
    private String city;
    private int click;
    private String img;
}
