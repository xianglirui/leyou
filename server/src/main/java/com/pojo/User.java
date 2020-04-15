package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private int id;
    private String name;
    private String password;
    private String email;
    private String salt;
    private String perms;
    private int role;//0游客  1商家  2春芽  3夏果  4秋枫  5暴雪

}
