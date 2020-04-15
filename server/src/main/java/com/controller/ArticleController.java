package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapper.LocalHotMapper;
import com.mapper.SearchMapper;
import com.pojo.*;
import com.service.ArticleService;
import com.service.CollectService;
import com.service.UserService;
import com.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private LocalHotMapper localHotMapper;
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private SearchMapper searchMapper;

    @PostMapping(value = "/subEdit")
    public String addArticle(@RequestBody Map<Object,Object> data){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String starttime = df.format(new Date());
        String html = (String) data.get("html");
        String title = (String) data.get("title");
        Boolean checks = (Boolean) data.get("check");
        String city = (String) data.get("city");
        System.out.println(data.get("list"));
        String img = data.get("list").toString();
        int check = 0;
        if(!checks){
            check = 1;
        }
        int uid = (int) data.get("uid");
        Article article = new Article();
        article.setArticle(html);
        article.setTitle(title);
        article.setReprint(check);
        article.setUid(uid);
        article.setTimes(starttime);
        article.setCity(city);
        article.setImg(img);
        articleService.addArticle(article);
        Search search = new Search();
        search.setAid(0);
        search.setTid(article.getId());
        search.setClick(0);
        searchMapper.add(search);
        HashMap<String,Object> hs = new HashMap<>();
        hs.put(search.getId()+"",0);
        redisUtil.hmset("关键词搜索次数",hs);

//        LocalHot localHot = new LocalHot();
//        localHot.setTid(article.getId());
//        localHot.setCity(city);
//        localHot.setClick(0);
//        localHot.setAid(0);
//        localHotMapper.add(localHot);

        HashMap<String,Object> map = new HashMap<>();
        map.put(article.getId()+"",0);
        redisUtil.hmset("游记点击",map);
        return "ok";
    }

    @PostMapping("/articleEdit")
    public String articleEdit(@RequestBody Map<Object,Object> data){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String starttime = df.format(new Date());
        String html = (String) data.get("html");
        String title = (String) data.get("title");
        Boolean checks = (Boolean) data.get("check");
        String city = (String) data.get("city");
        String img = data.get("list").toString();
        int check = 0;
        if(!checks){
            check = 1;
        }
        int id = (int) data.get("id");
        Article article = new Article();
        article.setArticle(html);
        article.setTitle(title);
        article.setReprint(check);
        article.setTimes(starttime);
        article.setCity(city);
        article.setImg(img);
        article.setId(id);
        articleService.editArticle(article);
        return "ok";
    }

    @GetMapping("/addRedisClick")
    public void addRedisClick(@RequestParam int tid){
        redisUtil.hincr("游记点击",tid+"",1);
    }

    @RequestMapping(value = "/article_Lists",method = RequestMethod.GET)
    public String selectList(@RequestParam int id) throws JsonProcessingException {
        System.out.println("收到展示用户游记");
        List<Article> articles = articleService.articleList(id);
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("list",articles);
        return new ObjectMapper().writeValueAsString(hs);
    }

    @RequestMapping(value = "/getArticle",method = RequestMethod.GET)
    public String getArticle(@RequestParam int id) throws JsonProcessingException {
        System.out.println("收到展示游记页面");
        Article articles = articleService.article(id);
//        System.out.println(articles);
        HashMap<String,Object> hs=new HashMap<>();
        if(articles==null){
            hs.put("err","null");
        }else {
            hs.put("article",articles);
        }
        return new ObjectMapper().writeValueAsString(hs);
    }

    @GetMapping("/updateZan")
    public String updateZan(@RequestParam int uid,@RequestParam int tid,@RequestParam int good){
        articleService.updateZan(good+1,tid);
        return "ok";
    }

    @GetMapping("/updateCollect")
    public String updateCollect(@RequestParam int uid,@RequestParam int tid,@RequestParam int collect){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String starttime = df.format(new Date());
        articleService.updateCollect(collect+1,tid);
        Collect collect2 = collectService.selectByUidAndTid(uid,tid);
        if(collect2==null){
            Collect collect1 = new Collect();
            collect1.setTimes(starttime);
            collect1.setTid(tid);
            collect1.setUid(uid);
            collectService.addCollect(collect1);
        }
        return "ok";
    }


    @GetMapping("/displayLocalTravel")
    public String displayLocalTravel(@RequestParam String city){
        HashMap<String,Object> hs = new HashMap<>();
        try {
            List<Article> articles = articleService.queryByCity(city);
            return new ObjectMapper().writeValueAsString(articles);
        } catch (Exception e) {
            return "err";
        }
    }

    @GetMapping("/queryNotCityOrderByClick")
    public String queryNotCityOrderByClick(@RequestParam String city) throws JsonProcessingException {
        List<Article> articleList = articleService.queryNotCityOrderByClick(city);
        return new ObjectMapper().writeValueAsString(articleList);
    }

    @GetMapping("/getArticleListByPage")
    public String getArticleListByPage(@RequestParam int pageCode) throws JsonProcessingException {
        PageInfo<Article> pageBean = articleService.queryArticle(pageCode,3);
        return new ObjectMapper().writeValueAsString(pageBean);
    }

    @GetMapping("/queryLocalArticle")
    public String queryLocalArticle(@RequestParam  String city) throws JsonProcessingException {
        List<Article> articles = articleService.queryLocalArticle(city);
        return new ObjectMapper().writeValueAsString(articles);
    }

    @GetMapping("/queryLocalArticleWithoutCity")
    public String queryLocalArticleWithoutCity() throws JsonProcessingException {
        List<Article> articleList = articleService.queryLocalArticleWithoutCity();
        return new ObjectMapper().writeValueAsString(articleList);
    }

}
