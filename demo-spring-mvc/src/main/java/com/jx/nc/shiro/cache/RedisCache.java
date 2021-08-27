package com.jx.nc.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisCache<K, V> implements Cache<K, V> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    private K matchKey;

    // 缓存的超时时间，单位为s
    private long expireTime = 120;

    @Override
    public V get(K key) throws CacheException {
        logger.debug("根据key:{}从redis获取对象", key);
        logger.debug("redisTemplate : {}", redisTemplate);
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public V put(K key, V value) throws CacheException {
        logger.debug("根据key:{}从redis添加对象", key);
        redisTemplate.opsForValue().set(key, value, this.expireTime, TimeUnit.SECONDS);
        return value;
    }

    /**
     * 执行set操作并且设置生存时间，单位为：秒
     * @param key
     * @param value
     * @param timeOut
     */
    public void setValueTime(K key, V value, long timeOut) {
        redisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.SECONDS);
    }

    @Override
    public V remove(K key) throws CacheException {
        logger.debug("redis cache remove :{}", key.toString());
        V value = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        return value;
    }

    public void clear(K matchKey) {
        this.matchKey = matchKey;
        clear();
    }
    @Override
    public void clear() throws CacheException {
        logger.debug("清除redis所有缓存对象");
        if(matchKey != null) {
            Set<K> keys = redisTemplate.keys(matchKey);
            redisTemplate.delete(keys);
        }
    }

    public int size(K matchKey) {
        this.matchKey = matchKey;
        return size();
    }
    @Override
    public int size() {
        if(matchKey != null) {
            Set<K> keys = redisTemplate.keys(matchKey);
            logger.debug("获取redis缓存对象数量:{}", keys.size());
            return keys.size();
        }
        return 0;
    }

    public Set<K> keys(K matchKey) {
        this.matchKey = matchKey;
        return keys();
    }
    @Override
    public Set<K> keys() {
        if(matchKey != null) {
            Set<K> keys = redisTemplate.keys(matchKey);
            logger.debug("获取所有缓存对象的key");
            if (keys.size() == 0) {
                return Collections.emptySet();
            }
            return keys;
        }
        return new HashSet<K>(0);
    }

    public Collection<V> values(K matchKey) {
        this.matchKey = matchKey;
        return values();
    }
    @Override
    public Collection<V> values() {
        if(matchKey != null) {
            Set<K> keys = redisTemplate.keys(matchKey);
            logger.debug("获取所有缓存对象的value");
            if (keys.size() == 0) {
                return Collections.emptySet();
            }
            List<V> vs = redisTemplate.opsForValue().multiGet(keys);

            return Collections.unmodifiableCollection(vs);
        }
        return Collections.emptySet();
    }

    public K getMatchKey() {
        return matchKey;
    }

    public void setMatchKey(K matchKey) {
        this.matchKey = matchKey;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
