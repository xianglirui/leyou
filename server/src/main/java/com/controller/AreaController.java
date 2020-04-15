package com.controller;

import com.config.MyUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapper.AreaMapper;
import com.mapper.ShopMapper;
import com.pojo.*;
import com.service.AreaService;
import com.service.PayPoolService;
import com.service.ShopService;
import com.service.UserService;
import com.utils.RedisUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@ApiIgnore
public class AreaController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private UserService userService;
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private PayPoolService payPoolService;
    @Autowired
    private ShopService shopService;
    @Resource
    private MyUtils myUtils;
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private ShopMapper shopMapper;

    @GetMapping("/area_")
    public String area(@RequestParam String param) throws JsonProcessingException {
        System.out.println("收到商家信息请求");
        //对token进行处理，获取用户名
        User user = userService.queryUserByName(param);
        Area area = areaService.queryArea(user.getId());

        ObjectMapper objectMapper=new ObjectMapper();

        Map<String,Object> hs = new HashMap<>();
        hs.put("area",area);
//        hs.put("user",user);
//        redisUtil.hmset("area_"+username,hs);
        return objectMapper.writeValueAsString(area);

    }

    @PostMapping("/area_form_sub")
    public String areaForm(@RequestBody Map<Object,Object> data){
        String name = (String) data.get("name");
        Area area = new ObjectMapper().convertValue(data.get("form"),Area.class);
//        Map<Object,Object> hs = redisUtil.hmget("area_"+username);
//        Area area1 = (Area) hs.get("area");
        Area area1 = new Area();
        area1.setUid(userService.queryUserByName(name).getId());
        area1.setDate(area.getDate());
        area1.setClosetime(area.getClosetime());
        area1.setOpentime(area.getOpentime());
        area1.setPrice(area.getPrice());
        area1.setNums(area.getNums());
        area1.setDescs(area.getDescs());
        area1.setTel(area.getTel());
        areaService.updateForm(area1);
        Shop shop = new Shop();
        shop.setAid(area.getId());
        shop.setClick(0);
        shopService.add(shop);
        return "ok";
    }

    @GetMapping("/selectById")
    public String selectById(@RequestParam int id,@RequestParam int param){
        System.out.println("收到id查询景区");
        Area area = areaService.queryAreaById(id);
        if(param==1){
            return area.getName();
        }else {
            return area.getDescs();
        }
    }

    @GetMapping("/getComplaint")
    public String getComplaint(@RequestParam int id) throws JsonProcessingException {
        HashMap<Object,Object> hs = new HashMap<>();
        Map<Object, Object> map = redisUtil.hmget("投诉"+id+"号商家");
        List<PayPool> payPoolList = payPoolService.queryByToAreaOne(id);
        hs.put("complaint",map);
        hs.put("refund",payPoolList);
        return new ObjectMapper().writeValueAsString(hs);
    }

    @GetMapping("/isTableHasData")
    public String isTableHasData(@RequestParam int uid){
        Area area = areaService.queryArea(uid);
        if(area==null){
            return "err";
        }else {
            return "ok";
        }
    }

    @GetMapping("/getPictureByAName")
    public String getPictureByAName(@RequestParam String name) throws UnsupportedEncodingException, JsonProcessingException {
        HashMap<String,Object> fileList = myUtils.getFileListByAName("areaInfo",name);
        return new ObjectMapper().writeValueAsString(fileList);
    }

    @GetMapping("/deletePictureByFileName")
    public void deletePictureByFileName(@RequestParam String aname,@RequestParam String fname){
        String filename = "/areaInfo/"+aname+"/"+fname;
        try {
            myUtils.deleteDocument(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/getLocalArea")
    public String getLocalArea(@RequestParam String city) throws JsonProcessingException {
        List<Shop> areaList = shopMapper.getHotArea(city);
        return new ObjectMapper().writeValueAsString(areaList);
    }

}
