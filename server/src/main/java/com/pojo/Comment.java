package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int id;
    private String times;
    private String content;
    private int oid;
//    private Article article;
//    private Area area;
    private int aid;
    private int tid;
    private int uid;
    private int star;
    private String back;
}
