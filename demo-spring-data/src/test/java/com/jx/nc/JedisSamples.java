package com.jx.nc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@ContextConfiguration({"classpath:spring-single-redis.xml"})
public class JedisSamples {

    @Autowired
    private Jedis jedis;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void Set() {
        jedis.set("hello", "spring");
    }

    @Test
    public void Get() {
        String s = jedis.get("hello");
        System.out.println(s);
    }

    @Test
    public void setEx() {
        String s = jedis.setex("spring", 10L, "我会在 10 秒后过期");
        System.out.println(s);
    }

    @Test
    public void queueTask() {
        redisTemplate.convertAndSend("java", "你好 南昌");
    }

}


