package com.controller;

import com.config.MyUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mapper.ItemRepository;
import com.mapper.Sell_TestMapper;
import com.pojo.PageInfo;
import com.pojo.Sell_Test;
import com.service.Sell_TestService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestController
public class VarController {
    @Autowired
    Sell_TestService sell_testService;


    @GetMapping("/getSell")
    public String getSell(@RequestParam int uid,@RequestParam int pageCode) throws JsonProcessingException {
        PageInfo<Sell_Test> pageBean = sell_testService.queryComment(pageCode,7,uid);
        return new ObjectMapper().writeValueAsString(pageBean);
    }


//    @GetMapping("/hello")
//    public void hello(){
//        elasticsearchTemplate.createIndex(Item.class);
//    }
//
//    @Autowired
//    private ItemRepository itemRepository;
//
//    /**
//     * @Description:定义新增方法
//     * @Author: https://blog.csdn.net/chen_2890
//     */
//    @GetMapping("/www")
//    public void insert() {
//        Item item = new Item(1L, "小米手机7", " 手机",
//                "小米", 3499.00, "http://image.baidu.com/13123.jpg");
//        itemRepository.save(item);
//    }






}
