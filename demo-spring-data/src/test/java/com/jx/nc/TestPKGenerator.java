package com.jx.nc;

import com.jx.nc.pkgenerate.DefaultPkGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring-standalone-redis.xml", "classpath:spring-single-redis.xml", "classpath:spring-pkgenerate-redis.xml"})
public class TestPKGenerator {

    @Autowired
    private DefaultPkGenerator defaultPkGenerator;

    @Test
    public void test2(){
//        IdGenerator idGeneratorx = new DefaultIdGenerator();
        //多线程测试
        System.out.println("--------多线程测试不重复------------------");
        long t1 = System.currentTimeMillis();
        Set<String> idSet = Collections.synchronizedSet(new HashSet<>());
        ExecutorService es = Executors.newFixedThreadPool(100);
        for (int i=0; i<200; i++){
//            System.out.println("进入: " + i);
            es.submit(() -> {
//                String val = idGenerator.next();
                String val = defaultPkGenerator.next();
                System.out.println(val);
                if (idSet.contains(val)){
                    System.out.println("重复了: " + val);
                }else{
                    idSet.add(val);
                }
            });
        }
        es.shutdown();
//        System.out.println("启用顺序关闭");
        while(true){
            if(es.isTerminated()){
//                System.out.println("所有的子线程都结束了！");
                break;
            }
//            try {
//                System.out.println("子线程的任务还没运行完");
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        System.out.println("共生成: " + idSet.size() + "个ID共耗时: " + (System.currentTimeMillis() - t1) + "ms");
    }

    @Test
    public void  test3(){
        //测试单机性能
        System.out.println("--------测试单线程性能------------------");
        long t1 = System.currentTimeMillis();
        int total = 2;
        for (int i=0; i<total; i++){
            System.out.println(defaultPkGenerator.next());
        }
        System.out.println("单线程生成" + total + "个ID共耗时: " + (System.currentTimeMillis() - t1) + "ms");
    }
}
