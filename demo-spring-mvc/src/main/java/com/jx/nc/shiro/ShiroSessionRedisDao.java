package com.jx.nc.shiro;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.jx.nc.shiro.cache.RedisCache;
import com.jx.nc.shiro.util.ShiroSessionConvertUtil;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ShiroSessionRedisDao implements ShiroSessionCacheDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisCache<String, String> redisCache;

    @Override
    public void saveSession(Session session) {
        String key = getRedisSessionKey(session.getId());
        String value = Base64.encode(ShiroSessionConvertUtil.sessionToByte(session));
        redisCache.put(key, value);
    }

    @Override
    public void deleteSession(Serializable sessionId) {
        String key = getRedisSessionKey(sessionId);
        redisCache.remove(key);
    }

    @Override
    public Session getSession(Serializable sessionId) {
        String key = getRedisSessionKey(sessionId);
        String value = redisCache.get(key);

        if(value == null || StrUtil.isEmpty(value)) {
            return null;
        }
        return ShiroSessionConvertUtil.byteToSession(Base64.decode(value));
    }

    @Override
    public Collection<Session> getAllSessions() {
        Set<Session> sessions = new HashSet<Session>();
        Set<String> byteKeys = redisCache.keys(ShiroConstant.SHIRO_CACHE_PREFIX + "*");
        if (byteKeys != null && byteKeys.size() > 0) {
            for (String key : byteKeys) {
                Session s = getSession(key);
                sessions.add(s);
            }
        }
        return sessions;
    }

    private String getRedisSessionKey(Serializable sessionId) {
        return ShiroConstant.SHIRO_CACHE_PREFIX + sessionId;
    }
}