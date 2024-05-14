package com.yupi.usercenter.onece;
import java.util.Date;


import com.yupi.usercenter.mapper.UserMapper;
import com.yupi.usercenter.model.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

@Component
public class InsertUser {
    @Resource
    UserMapper userMapper;

    public void doInsertUser(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int USER_COUNT=1000;
        for (int i = 0; i < USER_COUNT; i++) {
            User user = new User();
            user.setId(0L);
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
            userMapper.insert(user);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

}
