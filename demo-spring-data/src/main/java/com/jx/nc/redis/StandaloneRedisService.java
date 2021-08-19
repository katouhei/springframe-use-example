package com.jx.nc.redis;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 对SDR接口进行封装的工具类，来对redis进行操作
 */
@Component
public class StandaloneRedisService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 缓存value
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean cacheValue(String key, String value, long time) {
        try {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value);
            if (time > 0) {
                // 如果有设置超时时间的话
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable e) {
            logger.error("缓存[" + key + "]失败, value[" + value + "] " + e.getMessage());
        }
        return false;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 缓存value，没有设置超时时间
     *
     * @param k
     * @param value
     * @return
     */
    public boolean cacheValue(String k, String value) {
        return cacheValue(k, value, -1);
    }

    /**
     * 判断缓存是否存在
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Throwable e) {
            logger.error("判断缓存是否存在时失败key[" + key + "]", "err[" + e.getMessage() + "]");
        }
        return false;
    }

    /**
     * 根据key，获取缓存
     *
     * @param key
     * @return
     */
    public Object getValue(String key) {
        try {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            return valueOperations.get(key);
        } catch (Throwable e) {
            logger.error("获取缓存时失败key[" + key + "]", "err[" + e.getMessage() + "]");
        }
        return null;
    }

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    public boolean removeValue(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Throwable e) {
            logger.error("移除缓存时失败key[" + key + "]", "err[" + e.getMessage() + "]");
        }
        return false;
    }

    /**
     * 根据前缀移除所有以传入前缀开头的key-value
     *
     * @param pattern
     * @return
     */
    public boolean removeKeys(String pattern) {
        try {
            Set<String> keySet = redisTemplate.keys(pattern + "*");
            redisTemplate.delete(keySet);
            return true;
        } catch (Throwable e) {
            logger.error("移除key[" + pattern + "]前缀的缓存时失败", "err[" + e.getMessage() + "]");
        }
        return false;
    }

    public void setSeq(Integer seq) {
        cacheValue("seq", String.valueOf(seq));
    }

    public Integer getSeq() {
        Object seq = getValue("seq");
        if (seq != null && StrUtil.isNotEmpty(String.valueOf(seq)) && NumberUtil.isInteger(String.valueOf(seq))) {
            return NumberUtil.parseInt(String.valueOf(seq)) + 1;
        } else {
            return 1;
        }
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 使用redis的特性，生成主键
     *
     * @param prefix
     * @return
     */
    public String generatorDefaultId(String prefix) {
        return generatorId(prefix, "DEFAULT");
    }

    /**
     * 使用redis的特性，生成主键
     *
     * @param prefix
     * @param type
     * @return
     */
    public String generatorId(String prefix, String type) {
        String key = type == null ? "" : type + LocalDateTime.now().format(FORMATTER);
        Long seq = redisTemplate.opsForValue().increment(key, 1);
        if (seq == 1) {
            expire(key, 60 * 60 * 24);
        }
        StringBuffer sb = new StringBuffer(prefix == null ? "" : prefix).append(LocalDateTime.now().format(FORMATTER)).append(StrUtil.padPre(String.valueOf(seq), 7, "0"));
        return sb.toString();
    }

    /**
     * 消息队列用，新增内容入队列
     * @param content
     */
    public void convertAndSend(Object content) {
        redisTemplate.convertAndSend("queueTask", content);
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return this.redisTemplate;
    }

}

