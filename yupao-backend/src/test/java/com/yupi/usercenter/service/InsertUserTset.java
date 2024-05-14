package com.yupi.usercenter.service;


import com.yupi.usercenter.mapper.UserMapper;
import com.yupi.usercenter.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class InsertUserTset {
    @Resource
    UserService userService;

    @Test
    public void doInsertUser(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int USER_COUNT=100000;
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < USER_COUNT; i++) {
            User user = new User();
            user.setUsername("假测试数据");
            user.setUserAccount("fakeDate");
            user.setAvatarUrl("");
            user.setGender(0);
            user.setUserPassword("123456789");
            user.setPhone("13933338888");
            user.setEmail("123@qq.com");
            user.setUserStatus(0);
            user.setTags("[]");
            user.setUserRole(0);
            user.setPlanetCode("1");
            userList.add(user);
        }
        userService.saveBatch(userList,10000);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

}
