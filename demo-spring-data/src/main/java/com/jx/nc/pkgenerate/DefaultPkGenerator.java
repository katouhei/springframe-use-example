package com.jx.nc.pkgenerate;

import cn.hutool.core.util.StrUtil;
import com.jx.nc.pkgenerate.redis.SingleRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class DefaultPkGenerator extends DefaultPkGeneratorConfig implements PkGenerator, Runnable {

    private Logger log = LoggerFactory.getLogger(DefaultPkGeneratorConfig.class);

    private String time;

    private AtomicInteger value;

    private Thread thread;

    private DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(this.getPattern());

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private SingleRedisService singleRedisService;

    public DefaultPkGenerator(SingleRedisService singleRedisService) {
        time = LocalDateTime.now().format(FORMATTER);
        this.singleRedisService = singleRedisService;
        value = new AtomicInteger(singleRedisService.getSeq());
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public String next() {
        lock.readLock().lock();
        StringBuffer sb = new StringBuffer(this.getPrefix()).append(this.getSplitString()).append(time).append(this.getSplitString()).append(StrUtil.padPre(String.valueOf(value.getAndIncrement()), this.getFormatLength(), this.getFillString()));
        lock.readLock().unlock();
        return sb.toString();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000 * this.getRollingInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            singleRedisService.setSeq(value.get());
            String now = LocalDateTime.now().format(FORMATTER);
            if (!now.equals(time)) {
                lock.writeLock().lock();
                time = now;
                singleRedisService.setSeq(0);
                value.set(singleRedisService.getSeq());
                lock.writeLock().unlock();
            }
        }
    }
}
