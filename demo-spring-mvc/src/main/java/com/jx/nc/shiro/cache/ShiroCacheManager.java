package com.jx.nc.shiro.cache;

import cn.hutool.core.util.StrUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class ShiroCacheManager implements CacheManager, Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Cache cache;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("获取名称为: " + name + " 的RedisCache实例");
        if (!StrUtil.isNotEmpty(name)) {
            throw new IllegalArgumentException("Cache name cannot be null or empty.");
        }
        logger.debug("redis cache manager get cache name is :{}", name);
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
