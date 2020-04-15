package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapper.OrderItemMapper;
import com.pojo.OrderItem;
import com.pojo.PayPool;
import com.service.AreaService;
import com.service.OrderItemService;
import com.service.PayPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@ApiIgnore
public class OrderController {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private PayPoolService payPoolService;

    @RequestMapping("/ssr")
    public String hello(@RequestParam int uid) throws JsonProcessingException {
//        List<OrderItem> orderItemList = new ArrayList<>();
//        OrderItem orderItem = null;
//        for(int i=1;i<9;i++){
//            orderItem= orderItemService.queryOrderItem(i);
//            orderItemList.add(orderItem);
//        }
//        return orderItemList.toString();
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(orderItemService.orderList(uid));
    }

    @GetMapping("/refund")
    public String refund(@RequestParam int id) throws JsonProcessingException {
        orderItemService.updateRefund(1,id);
        return "ok";
    }

    @PostMapping("/commentArea")
    public String commentArea(@RequestBody String[] form) throws JsonProcessingException {
        System.out.println(Arrays.toString(form));
        orderItemService.updateComment(form[0],Integer.parseInt(form[1]),3,form[2]);
//        System.out.println(Arrays.toString(form));
//        ObjectMapper objectMapper=new ObjectMapper();
//        return objectMapper.writeValueAsString(form);
        return "ok";
    }


    @GetMapping("/addOrderItem")
    public String addOrderItem(@RequestParam int aid,@RequestParam int uid,@RequestParam String money,@RequestParam int payway,@RequestParam int nums){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String starttime = df.format(new Date());
        OrderItem orderItem =new OrderItem();
        orderItem.setAccount(Float.parseFloat(money));
        orderItem.setUid(uid);
        orderItem.setTimes(starttime);
        orderItem.setAid(aid);
        orderItem.setPayWay(payway);
        orderItem.setNums(nums);
        System.out.println(orderItem);
        int id = orderItemService.add(orderItem);

        PayPool payPool = new PayPool();
        payPool.setMoney(money);
        payPool.setOid(orderItem.getId());
        payPool.setState(0);
        payPool.setTimes(starttime);
        payPool.setUid(uid);
        payPool.setAid(aid);
        payPoolService.add(payPool);
        return "ok";
    }

    @GetMapping("/updateState")
    public void updateState(@RequestParam int oid){
        //已使用待评价
        orderItemService.updateState(1,oid);
    }
}
