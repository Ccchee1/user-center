package com.yupi.usercenter.service;

import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class RedissonTest {
    @Autowired
    RedissonClient redissonClient;

    @Test
    public void testRedisson(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("old");
        System.out.println("list0:" + arrayList.get(0));
        arrayList.set(0,"new");

        RList<String> list = redissonClient.getList("redisson-list-test1");
        list.add("redis-old");
        String o = list.get(0);
        System.out.println(o);


    }


}
