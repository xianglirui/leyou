package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.Collect;
import com.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class CollectController {
    @Autowired
    private CollectService collectService;

    @GetMapping("/addCollect")
    public String addCollect(@RequestParam int uid,@RequestParam int aid) throws JsonProcessingException {
        System.out.println("收到景区收藏");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String starttime = df.format(new Date());
        HashMap<Object,Object> hs = new HashMap<>();
        Collect collect2 = collectService.selectByUidAndTid(uid,aid);
        if(collect2==null){
            Collect collect1 = new Collect();
            collect1.setTimes(starttime);
            collect1.setAid(aid);
            collect1.setUid(uid);
            collectService.addCollect(collect1);
            hs.put("ok",1);
        }else {
            hs.put("err",2);
        }
        return new ObjectMapper().writeValueAsString(hs);
    }

    @GetMapping("/selectCollect")
    public String selectCollect(@RequestParam int uid,@RequestParam int tid) throws JsonProcessingException {
        System.out.println("收到游记收藏");
        HashMap<Object,Object> hs = new HashMap<>();
        Collect collect = collectService.selectByUidAndTid(uid,tid);
        if(collect!=null){
            hs.put("collect",collect);
        }else {
            hs.put("err","1");
        }

        return new ObjectMapper().writeValueAsString(hs);
    }

    @GetMapping("/selectCollectList")
    public String selectCollectList(@RequestParam int id) throws JsonProcessingException {
        List<Collect> collectList = collectService.collectList(id);
        return new ObjectMapper().writeValueAsString(collectList);
    }

    @GetMapping("/removeCollect")
    public String removeCollect(@RequestParam int aid,@RequestParam int tid){
        System.out.println("删除收藏");
        //不用delete，将zan属性设置一下，查询时看看zan是不是等于0，不等于就不查出来
        collectService.updateZan(tid,aid);
        return "ok";
    }
}
