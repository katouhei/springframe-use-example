package com.jx.nc;

import cn.hutool.core.date.DateUtil;
import com.jx.nc.pkgenerate.redis.StandaloneRedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.format.DateTimeFormatter;
import java.util.Date;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring-standalone-redis.xml", "classpath:spring-single-redis.xml", "classpath:spring-pkgenerate-redis.xml"})
public class TestStandaloneUtils {

    @Autowired
    private StandaloneRedisService standaloneRedisService;


    @Test
    public void test3() {
        String key = "SEQ"+DateUtil.format(new Date(), "yyyyMMddHHmm");
        new TestStandlone().run();
    }

    class TestStandlone implements Runnable {

        private DateTimeFormatter FORMATTER;

        public TestStandlone() {
            FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000 * 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String key = "SEQ" + DateUtil.format(new Date(), "yyyyMMddHHmm");
                Long seq = standaloneRedisService.getRedisTemplate().opsForValue().increment(key, 1);
                if (seq == 1) {
                    standaloneRedisService.expire(key, 60);
                } else if(seq > 5){
                    break;
                }
                System.out.println(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"的时间："+key + "键的SEQ序列值" + seq);

            }
        }
    }
}
