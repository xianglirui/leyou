package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collect {
    private int id;
    private int uid;
    private int aid;
    private int tid;
    private String times;
    private int zan;//移出收藏
}
