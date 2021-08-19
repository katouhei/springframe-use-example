package com.jx.nc.pkgenerate;

import cn.hutool.core.util.StrUtil;
import com.jx.nc.redis.StandaloneRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class DefaultPkGenerator extends DefaultPkGeneratorConfig implements PkGenerator, Runnable {

    private Logger log = LoggerFactory.getLogger(DefaultPkGeneratorConfig.class);

    private String time;

    private AtomicInteger value;

    private Thread thread;

    private DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(this.getPattern());

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Autowired
    private StandaloneRedisService standaloneRedisService;

    public void init() {
        time = LocalDateTime.now().format(FORMATTER);
        value = new AtomicInteger(standaloneRedisService.getSeq());
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public void close() {
        standaloneRedisService.setSeq(value.get());
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
            standaloneRedisService.setSeq(value.get());
            String now = LocalDateTime.now().format(FORMATTER);
            if (!now.equals(time)) {
                lock.writeLock().lock();
                time = now;
                standaloneRedisService.setSeq(0);
                value.set(standaloneRedisService.getSeq());
                lock.writeLock().unlock();
            }
        }
    }
}
