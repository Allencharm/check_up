package com.checkup.job;

import com.checkup.util.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Set;

/**
 * @author ldq
 * @version 1.0
 * @date 2024-8-3 22:27
 * @Description:
 */
@Component
public class RedisJob {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${setmeal_img_path}")
    private String path;

    //作业删除redis多余图片名称和多余图片
    //@Scheduled(cron = "0 0 1 * * ?")//每天凌晨一点清理操作执行
    //每隔五秒清理一次
    @Scheduled(cron = "0/5 * * * * ?")
    public void clearRedis(){
        //求差集
        Set<String> diff = stringRedisTemplate.boundSetOps(RedisConstant.UPLOAD_KEY).diff(RedisConstant.Add_KEY);
        //从差集中找文件名实现删除
        for (String name : diff) {
            stringRedisTemplate.boundSetOps(RedisConstant.UPLOAD_KEY).remove(name);
            File file = new File(path, name);
            file.delete();
        }
    }
}
