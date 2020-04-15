package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.PayPool;
import com.service.ComplaintService;
import com.service.OrderItemService;
import com.service.PayPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PayWayController {
    @Autowired
    private PayPoolService payPoolService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/getPayPool")
    public String addPayPool() throws JsonProcessingException {
        List<PayPool> payPoolList = payPoolService.payPoolList();
        return new ObjectMapper().writeValueAsString(payPoolList);
    }

    @GetMapping("/okRefund")
    public void okRefund(@RequestParam int id,@RequestParam int oid){
        payPoolService.updateState(id,-4);
        orderItemService.updateRefund(3,oid);
    }

    @GetMapping("/toAreaRefund")
    public void toAreaRefund(@RequestParam int id){
        payPoolService.updateToArea(id);
    }

    @GetMapping("/AreaAgreeRefund")
    public void AreaAgreeRefund(@RequestParam int id){
        payPoolService.updateState(id,-2);
    }
}
