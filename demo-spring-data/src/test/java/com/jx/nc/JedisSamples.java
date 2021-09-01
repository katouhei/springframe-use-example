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
        jedis.expire("hello", 300l);
        jedis.set("testhello", "testhellospring");
        jedis.expire("testhello", 3300l);
        jedis.set("abc", "1111111111111111aaaaaaaaaaaaaaa2222222222222");
    }

    @Test
    public void Get() {
        String s = jedis.get("hello");
        System.out.println(s);
        s = jedis.get("testhello");
        System.out.println(s);
        s = jedis.get("abc");
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


