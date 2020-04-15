package com.mapper;

import com.github.pagehelper.Page;
import com.pojo.Shop;
import jdk.jfr.Registered;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
@Registered
public interface ShopMapper {
    Shop queryShop(int id);
    Page<Shop> queryComment(int aid);
    void add(Shop shop);
    void update(int click,int aid);
    List<Shop> shopListOrder();
    List<Shop> getHotArea(String city);
}
