package com.jx.nc;

import com.jx.nc.idgenerate.DefaultIdGenerator;
import com.jx.nc.idgenerate.DefaultIdGeneratorConfig;
import com.jx.nc.idgenerate.IdGenerator;
import com.jx.nc.idgenerate.IdGeneratorConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring-standalone-redis.xml", "classpath:spring-idgenerator-redis.xml"})
public class TestIdGenerator {

    @Resource
    private IdGenerator idGenerator;

    @Test
    public void Test() throws Exception {
        String id = idGenerator.next();
        System.out.println(id);
    }

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
                String val = idGenerator.next();
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
        int total = 200;
        for (int i=0; i<total; i++){
            System.out.println(idGenerator.next());
        }
        System.out.println("单线程生成" + total + "个ID共耗时: " + (System.currentTimeMillis() - t1) + "ms");
    }

    @Test
    public void test6(){
        System.out.println("--------ID生成器的特殊设置相关----------");
        IdGeneratorConfig config = new DefaultIdGeneratorConfig() {
            @Override
            public String getSplitString() {
                return "-";
            }
            @Override
            public int getInitial() {
                return 1000000;
            }
            @Override
            public String getPrefix() {
                return "NODE01";
            }
        };
        IdGenerator idGenerator = new DefaultIdGenerator(config);
        for (int i=0; i<20; i++){
            String id = idGenerator.next();
            System.out.println(id);
            try {
                Thread.sleep(1000 * 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
