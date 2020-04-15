package com.controller;

import com.mapper.LocalHotMapper;
import com.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LocalHotController {
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private LocalHotMapper localHotMapper;


//    @GetMapping("/addRedisClick")
//    public void addRedisClick(@RequestParam int tid){
//        redisUtil.hincr(tid+"号游记","click",1);
//    }
}
