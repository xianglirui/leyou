package com.config;

import com.service.ArticleService;
import com.service.ShopService;
import com.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
public class DynamicScheduleTask {
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ShopService shopService;
    @Scheduled(cron = "0 0/10 * * * ?")//每十分钟更新
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        Map<Object,Object> map = redisUtil.hmget("游记点击");
        Set<Map.Entry<Object, Object>> entryseSet=map.entrySet();
        for (Map.Entry<Object, Object> entry:entryseSet) {
            articleService.updateClick(Integer.parseInt(entry.getValue().toString()),Integer.parseInt(entry.getKey().toString()));
        }

        Map<Object,Object> map1 = redisUtil.hmget("景区点击");
        Set<Map.Entry<Object, Object>> entrySet=map1.entrySet();
        for (Map.Entry<Object, Object> entry:entrySet) {
            shopService.update(Integer.parseInt(entry.getValue().toString()),Integer.parseInt(entry.getKey().toString()));
        }
    }




}
