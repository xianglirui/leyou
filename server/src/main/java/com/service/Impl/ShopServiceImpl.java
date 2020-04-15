package com.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mapper.ShopMapper;
import com.pojo.PageInfo;
import com.pojo.Shop;
import com.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public Shop queryShop(int id) {
        return shopMapper.queryShop(id);
    }

    @Override
    public List<Shop> shopListOrder() {
        return shopMapper.shopListOrder();
    }


    @Override
    public void add(Shop shop) {
        shopMapper.add(shop);
    }

    @Override
    public void update(int click,int aid) {
shopMapper.update(click,aid);
    }

    @Override
    public PageInfo<Shop> queryComment(int pageCode, int pageSize,int aid) {
        PageHelper.startPage(pageCode,pageSize);
        Page<Shop> page = shopMapper.queryComment(aid);
        return new PageInfo<Shop>(page.getTotal(),page.getResult());
    }
}
