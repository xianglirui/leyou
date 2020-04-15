package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.Complaint;
import com.pojo.PayPool;
import com.service.ArticleService;
import com.service.ComplaintService;
import com.service.OrderItemService;
import com.service.PayPoolService;
import com.utils.RedisUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ComplaintController {
    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ArticleService articleService;
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private PayPoolService payPoolService;

    @GetMapping("/select_complaint_list")
    public String select() throws JsonProcessingException {
        List<Complaint> complaintList = complaintService.compList();
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(complaintList);
    }

    @GetMapping("/select_complaint")
    public String selectByUid(@RequestParam int uid) throws JsonProcessingException {
        List<Complaint> complaintList = complaintService.queryByUid(uid);
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(complaintList);
    }

    @ApiOperation(value = "complaint")
    @ApiImplicitParam(name="complaint",dataType = "Complaint")
    @PostMapping("/add_complaint")
    public String add(@RequestBody Complaint complaint){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String starttime = df.format(new Date());
        complaint.setStarttime(starttime);
        complaintService.add(complaint);
        return "ok";
    }

    @GetMapping("/updateFeedBack")
    public String updateFeedBack(@RequestParam String feedback,@RequestParam int id,@RequestParam int oid){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String starttime = df.format(new Date());
        complaintService.updateFeedBack(id,feedback,starttime);
        orderItemService.updateState(3,oid);
        return "ok";
    }

    @GetMapping("/updateArticleShow")
    public String updateArticleShow(@RequestParam int id,@RequestParam int tid){
        articleService.updateDis(tid);
        complaintService.updateState(id);
        return "ok";
    }

    @PostMapping("/toAreaMsg")
    public String toAreaMsg(@RequestBody Complaint complaint){
        if(complaint.getAid()==0){
            return "err";
        }else {
            Map<String,Object> hs = new HashMap<>();
            hs.put(complaint.getStarttime(),complaint);
            redisUtil.hmset("投诉"+complaint.getAid()+"",hs);
            return "ok";
        }
    }
    @GetMapping("/areaAmend")
    public void areaAmend(@RequestParam String index,@RequestParam int aid){
        redisUtil.hdel("投诉"+aid+"号商家",index);
    }

    @GetMapping("/updateAdminId")
    public String updateAdminId(@RequestParam int adid,@RequestParam int id){
        complaintService.updateAdminId(adid,id);
        return "ok";
    }

    @PostMapping("/addPayPoolByComplaint")
    public String addPayPoolByComplaint(@RequestBody Complaint complaint){
        PayPool payPool = new PayPool();
        payPool.setUid(complaint.getUid());
        payPool.setTimes(complaint.getStarttime());
        payPool.setOid(complaint.getOid());
        payPool.setMoney(orderItemService.queryById(complaint.getOid()).getAccount()+"");
        payPool.setToarea(0);
        payPool.setState(-1);
        payPoolService.add(payPool);
        return "ok";
    }
}
