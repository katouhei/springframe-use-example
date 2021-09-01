package com.jx.nc.shiro;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.jx.nc.shiro.cache.JedisCache;
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

public class ShiroSessionJedisDao implements ShiroSessionCacheDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JedisCache<String, String> jedisCache;

    private long sessionTimeOut = 60 * 10;

    @Override
    public void saveSession(Session session) {
        String key = getRedisSessionKey(session.getId());
        String value = Base64.encode(ShiroSessionConvertUtil.sessionToByte(session));
        jedisCache.put(key, value);
    }

    @Override
    public void deleteSession(Serializable sessionId) {
        String key = getRedisSessionKey(sessionId);
        jedisCache.remove(key);
    }

    @Override
    public Session getSession(Serializable sessionId) {
        String key = getRedisSessionKey(sessionId);
        String value = jedisCache.get(key);

        if(value == null || StrUtil.isEmpty(value)) {
            return null;
        }
        return ShiroSessionConvertUtil.byteToSession(Base64.decode(value));
    }

    @Override
    public Collection<Session> getAllSessions() {
        Set<Session> sessions = new HashSet<Session>();
        Set<String> byteKeys = jedisCache.keys(ShiroConstant.SHIRO_CACHE_PREFIX + "*");
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
