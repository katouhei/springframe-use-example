package com.jx.nc.shiro.cache;

import cn.hutool.core.util.StrUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class RedisCacheManager implements CacheManager, Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisCache redisCache;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("获取名称为: " + name + " 的RedisCache实例");
        if (!StrUtil.isNotEmpty(name)) {
            throw new IllegalArgumentException("Cache name cannot be null or empty.");
        }
        logger.debug("redis cache manager get cache name is :{}", name);
        return redisCache;
    }
}
