package com.jx.nc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-standalone-redis.xml"})
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test1(){

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("first","hellow word");
        System.out.println(valueOperations.get("first"));
    }

    @Test
    public void test2(){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set("secound","hello everyone");
        System.out.println(stringStringValueOperations.get("secound"));
    }
}
