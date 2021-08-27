package com.jx.nc.shiro;

import cn.hutool.core.util.StrUtil;
import com.jx.nc.shiro.cache.RedisCache;
import com.jx.nc.shiro.util.ShiroSessionConvertUtil;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ShiroSessionRedisDao implements ShiroSessionDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisCache<byte[], byte[]> redisCache;

    private long sessionTimeOut = 60 * 10;

    @Override
    public void saveSession(Session session) {
        byte[] key = StrUtil.bytes(getRedisSessionKey(session.getId()));
        byte[] value = ShiroSessionConvertUtil.sessionToByte(session);
        redisCache.setValueTime(key, value, sessionTimeOut);
    }

    @Override
    public void deleteSession(Serializable sessionId) {
        byte[] key = StrUtil.bytes(getRedisSessionKey(sessionId));
        redisCache.remove(key);
    }

    @Override
    public Session getSession(Serializable sessionId) {
        byte[] key = StrUtil.bytes(getRedisSessionKey(sessionId));
        byte[] value = redisCache.get(key);
        return ShiroSessionConvertUtil.byteToSession(value);
    }

    @Override
    public Collection<Session> getAllSessions() {
        Set<Session> sessions = new HashSet<Session>();
        Set<byte[]> byteKeys = redisCache.keys(StrUtil.bytes(ShiroConstant.SHIRO_CACHE_PREFIX + "*"));
        if (byteKeys != null && byteKeys.size() > 0) {
            for (byte[] key : byteKeys) {
                Session s = ShiroSessionConvertUtil.byteToSession(redisCache.get(key));
                sessions.add(s);
            }
        }
        return sessions;
    }

    private String getRedisSessionKey(Serializable sessionId) {
        return ShiroConstant.SHIRO_CACHE_PREFIX + sessionId;
    }
}