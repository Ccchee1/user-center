package com.yupi.usercenter.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * description: 这个类的作用是执行定时任务每天去预热数据，虽然我们做了缓存但是当用户在第一次进入主页推荐页面的时候还是会去查数据库
 *              这样就会导致第一次查询缓慢，影响用户体验，所以我们做了一个缓存预热，将用户的推荐也缓存到redis数据库中，这样就不会
 *              第一次卡顿了
 */
@Component
@Slf4j
public class PreCacheJob {
    @Resource
    UserService userService;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    RedissonClient redissonClient;
    //主要用户，之后可能是一个列表，目前先写定
    private List<Long> mainUserList = Arrays.asList(1L);


    //每天执行,预热推荐用户
    @Scheduled(cron = "0 5 * * * *")   //自己设置时间测试
    public void doCacheCommendUser(){
        RLock lock = redissonClient.getLock("yupao.precachejob:docache:lock");
        try {
            //这里的第一个参数（waittime）设置为0，意味着如果抢不到的话就直接放弃,，然后第二个参数leasetime设置为-1表示可以自动续期
            if (lock.tryLock(0,-1, TimeUnit.MILLISECONDS)) {
                System.out.println("getlock:" + Thread.currentThread().getId());
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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //这里首先要注意为了防止上面try中的执行出错而导致锁一直被占据，就需要我们把释放锁的逻辑放在finallu中
            //又为了防止释放别人的锁,所以进行判断，底层是判断死否是自己所属的线程id
            if (lock.isHeldByCurrentThread()){
                System.out.println("unlock:" + Thread.currentThread().getId());
                lock.unlock();
            }
        }

    }
}
