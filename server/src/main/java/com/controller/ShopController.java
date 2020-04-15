package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapper.CollectMapper;
import com.mapper.CommentMapper;
import com.mapper.OrderItemMapper;
import com.pojo.OrderItem;
import com.pojo.PageInfo;
import com.pojo.Shop;
import com.service.ShopService;
import com.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@ApiIgnore
public class ShopController {
    @Autowired
    private ShopService shopService;

    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CollectMapper collectMapper;

    @GetMapping("/shopDisplay")
    public String hello(@RequestParam int aid) throws JsonProcessingException {
        Shop shop = shopService.queryShop(aid);
        if(shop!=null){
            String areaName = shop.getArea().getName();
            Map<String,Object> hs=new HashMap<>();
            Map<Object,Object> s = redisUtil.hmget("景区点击");
            if(!s.isEmpty()){
                redisUtil.hincr("景区点击",aid+"",1);
            }else {
                hs.put(aid+"",shop.getClick());
                redisUtil.hmset("景区点击",hs);
            }

            ObjectMapper objectMapper=new ObjectMapper();
            return objectMapper.writeValueAsString(shop.getArea());
        }else {
            return "err";
        }

    }

    @RequestMapping("/dnf")
    public String dnf(@RequestParam int aid) throws JsonProcessingException {
        PageInfo<Shop> pageBean = shopService.queryComment(1,3,aid);
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(pageBean);
    }

    @GetMapping("/changeCurrentPage")
    public String changeCurrentPage(@RequestParam int aid,@RequestParam int pageCode) throws JsonProcessingException {
        PageInfo<Shop> pageBean = shopService.queryComment(pageCode,3,aid);
        return new ObjectMapper().writeValueAsString(pageBean);
    }


    @GetMapping("/getHotPlace")
    public String getHotPlace() throws JsonProcessingException {
        List<Shop> shopList = shopService.shopListOrder();
        return new ObjectMapper().writeValueAsString(shopList);
    }

    @GetMapping("/getCollectAndOrderAndComment")
    public String getCollectAndOrderAndComment(@RequestParam int aid) throws JsonProcessingException {
        List<Integer> countList = new ArrayList<>();
        countList.add(commentMapper.selectCount(aid));
        countList.add(orderItemMapper.selectCount(aid));
        countList.add(collectMapper.selectCount(aid));
        return new ObjectMapper().writeValueAsString(countList);
    }
}
