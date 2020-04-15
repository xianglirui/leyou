package com.controller;

import com.config.MyUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapper.SearchMapper;
import com.pojo.*;
import com.qiniu.util.Auth;
import com.service.AreaService;
import com.service.ShopService;
import com.service.StepService;
import com.service.UserService;
import com.utils.RedisUtil;
import org.apache.ibatis.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//如果localstorm里没有token值
// 1.created里啥事没有，浏览器显示请登录
// 2.点击登录，存储token值，登录页面返回apply页面，会走created，因为有了token值，此时发起/select_active请求
// 3.在select_active方法里，第一：对token处理，将username取出来，等会儿查询数据库，第二：先根据username看看缓存里对应的值，
//   有就取出来，没有就进行mysql的查询，将查询出来的值保存到redis；第三步：返回active，如果active=-1，说明该商家还不存在customer表中，
//   前端拿到-1后就会走/select_customer，insert信息到数据库，并将信息保存在缓存（/select_customer只会被调用一次）@RestController
@RestController
@ApiIgnore
public class StepController {

    @Autowired
    private StepService stepService;
    @Autowired
    private UserService userService;
    @Autowired
    private AreaService areaService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private MyUtils myUtils;
    @Autowired
    private ShopService shopService;
    @Autowired
    private SearchMapper searchMapper;

    @GetMapping("/select_active")
    public String select_active(@RequestParam String param){
        System.out.println("收到created请求");
        Map<String,Object> hs=new HashMap<>();

        int active;
//        Map<Object,Object> c_r=redisUtil.hmget("step_active"+ param);
//        //页面刷新就会调用此方法，有频繁查询，所以可以先查询redis,redis中没有就去查询mysql
//        if(!c_r.isEmpty()){//map==null表示没有给new分配空间，判断有无内容用isEmpty
//            active = (int) c_r.get("active");
//        }else {
//            User user = userService.queryUserByName(param);
//            try{
//                Step step = stepService.queryStep(user.getId());
//                active = step.getActive();
//                hs.put("active",active);
//                redisUtil.hmset("step_active"+ param,hs);
//            }catch (Exception e){
//                active = -1;
//            }
//        }
        User user = userService.queryUserByName(param);
        try{
            Step step = stepService.queryStep(user.getId());
            active = step.getActive();
        }catch (Exception e){
            active = -1;
        }
        return active+"";
    }

    @GetMapping("/update_active")
    public String updateActive(@RequestParam String param) {
        System.out.println("收到next请求");

//        Map<Object, Object> customer_redis = redisUtil.hmget("step_active"+ param);
//        System.out.println(customer_redis);
//        int active = (int) customer_redis.get("active");

        //active+1
//        if(active==3){//active=3就可以了，因为第三步到第四步之间有审核时间
//            Step step = new Step();
//            User user = userService.queryUserByName(param);
//            step.setUid(user.getId());
//            step.setActive(active+1);
//            step.setIsFile(1);
//            step.setIsForm(1);
//            stepService.update(step);
//        }
//        redisUtil.hincr("step_active"+ param,"active",1);
        User user = userService.queryUserByName(param);
        Step step = stepService.queryStep(user.getId());
        int active = step.getActive();
        stepService.update1(active+1,user.getId());
        return active+1+"";
    }

    @PostMapping("/area_from")
    @ResponseBody
    public String cus_form(@RequestBody Map<Object, Object> form){
        System.out.println("用户表单："+form);
        String n = (String) form.get("username");
        //应该在admin那检查提交的信息，然后将景区名、地址、负责人信息写入areaInfo表
        Map<String,Object> hs=new HashMap<>();
        hs.put(n,form);
        redisUtil.hmset("注册商家",hs);
        return "ok";
    }

    //如果数据表里有该username的数据，就不会执行该方法
    @GetMapping("/select_step")
    public String add_customer(@RequestParam String param){
        System.out.println("收到logins请求");

        HashMap<String,Object> hs=new HashMap<>();
        User user = userService.queryUserByName(param);
        int active;

        try{
            Step step = stepService.queryStep(user.getId());
            active = step.getActive();
            hs.put("active",active);
        }catch (Exception e){
            //如果该商家不在customer表里
            Step step1 = new Step();
            step1.setUid(user.getId());
            stepService.insert(step1);
            active = 0;
            hs.put("active",active);
        }
//        redisUtil.hmset("step_active"+ param,hs);

        return active+"";
    }

    @GetMapping("/qiniu_token")
    public String qiniu(){
        String accessKey = "DdOsfnc82xUFTBBZ2VriRHVn4_zPk9qXGXCh5Hvn";
        String secretKey = "kWooGUSKxHYgaYssq8o2XtpQYIzi6mtLh5ABOKbK";
        String bucket = "xlr";
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucket);
    }

    @GetMapping("/getRedisAreaInfo")
    public String getRedisAreaInfo() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(redisUtil.hmget("注册商家"));
    }

    @PostMapping("/passRegister")
    //@RequestBody 多参数时可用 Map<Object,Object> ，但转换的类型时LinkHashMap,无法转换成实体类，可以用ObjectMapper().convertValue将LinkHashMap转换为实体类
    public String passRegister(@RequestBody Map<Object,Object> data){
        Area area = new ObjectMapper().convertValue(data.get("area"),Area.class);
        Area area1 = areaService.queryAreaByName(area.getName());
        Shop shop = new Shop();
        Search search = new Search();
        if(area1==null){
            areaService.add(area);
            search.setAid(area.getId());
            search.setTid(0);
            search.setClick(0);
            searchMapper.add(search);
            HashMap<String,Object> hs = new HashMap<>();
            hs.put(search.getId()+"",0);
            redisUtil.hmset("关键词搜索次数",hs);
            shop.setAid(area.getId());
            shopService.add(shop);
            redisUtil.hdel("注册商家",data.get("name"));
        }
        return "ok";
    }
    @PostMapping("/rejectRegister")
    public String rejectRegister(@RequestBody Map<Object,Object> data){
        redisUtil.hdel("注册商家",data.get("name"));
        return "ok";
    }

    @GetMapping("/downLoadApplyDocument")
    public String downLoadApplyDocument(@RequestParam String name) throws UnsupportedEncodingException, JsonProcessingException {
        HashMap<String,Object> fileList = myUtils.getFileListByAName("apply",name);
        return new ObjectMapper().writeValueAsString(fileList);
    }


}
