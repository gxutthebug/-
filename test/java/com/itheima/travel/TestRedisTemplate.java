package com.itheima.travel;

import com.itheima.travel.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class TestRedisTemplate {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testStringValue() {
        // 获取字符串值操作对象
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        // 添加值
        ops.set("person","张三");
        // 获取值打印
        Object person = ops.get("person");
        System.out.println(person);  // 张三

        // 如果不存在才添加
        ops.setIfAbsent("person", "李四");
        System.out.println(person);  // 张三
    }
}
