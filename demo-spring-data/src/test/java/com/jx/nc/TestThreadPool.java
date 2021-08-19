package com.jx.nc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ThreadPoolExecutor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-thread-pool.xml"})
public class TestThreadPool {

    @Autowired
    private ThreadPoolTaskExecutor threadPool;

    public static int i = 0;


    @Test
    public void test1() {
        for(int i =0; i < 50; i++) {
            threadPool.execute(new Thread() {
                @Override
                public void run() {
                    System.out.println(this.getName()+"@@@@@"+(TestThreadPool.i++));
                }
            });
        }
    }
}


