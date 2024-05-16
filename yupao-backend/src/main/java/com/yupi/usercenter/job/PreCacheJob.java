package com.yupi.usercenter.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PreCacheJob {
    @Resource
    UserService userService;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    //主要用户，之后可能是一个列表，目前先写定
    private List<Long> mainUserList = Arrays.asList(1L);


    @Scheduled(cron = "0 12 1 * * *")   //自己设置时间测试
    public void doCacheCommendUser(){
        //查数据库预热数据
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String redisKey = String.format("yupi.user.recommend.%s", mainUserList);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Page<User> userPage = userService.page(new Page<>(1, 20), queryWrapper);
        try {
            //这里记得要给redis的值设置过期时间，否则容易造成内存泄露
            valueOperations.set(redisKey,userPage,30000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("redis插入错误");
        }
    }
}
