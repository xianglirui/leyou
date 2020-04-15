package com.service;

import com.pojo.PageInfo;
import com.pojo.Shop;

import java.util.List;

public interface ShopService {
    Shop queryShop(int id);
    List<Shop> shopListOrder();
//    Shop queryComment(int aid);
    void add(Shop shop);
    void update(int click,int aid);
    PageInfo<Shop> queryComment(int pageCode, int pageSize,int aid);
}
