package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sell_Test {
    private int id;
    private int uid;

    private String times;

    private int outs;

    private int other;

    private int aveprice;

    private int ordernums;
}
