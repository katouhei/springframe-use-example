package com.jx.nc.shiro.cache;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.*;

public class JedisCache<K, V> implements Cache<K, V> {


    @Autowired
    private Jedis jedis;

    private K matchKey;

    // 缓存的超时时间，单位为s
    private long expireTime = 120;

    @Override
    public V get(K key) throws CacheException {
        String rawValue = jedis.get(key.toString());
        if(rawValue == null) {
            return null;
        }
        V value = (V) Convert.convert(Object.class, rawValue);
        return value;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        String k = key.toString();
        String v = StrUtil.toString(value);
        jedis.set(k, v);
        if (expireTime > 0) {
            jedis.expire(k, expireTime);
        }
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
        V value = get(key);
        String k = key.toString();
        jedis.del(k);
        return value;
    }

    @Override
    public void clear() throws CacheException {
        if (matchKey != null) {
            String mk = matchKey.toString();
            jedis.del(mk);
        }
    }

    @Override
    public int size() {
        if (matchKey != null) {
            String mk = matchKey.toString();
            Set<String> setb = jedis.keys(mk);
            if (setb != null) {
                return setb.size();
            }
        }
        return 0;
    }

    public Set<K> keys(K matchKey) {
        this.matchKey = matchKey;
        return keys();
    }

    @Override
    public Set<K> keys() {
        if (matchKey != null) {
            String mk = matchKey.toString();
            Set<String> setb = jedis.keys(mk);
            if (setb == null || setb.size() == 0) {
                return Collections.emptySet();
            }
            Set<K> setk = new HashSet<>();
            for (String br : setb) {
                setk.add((K) Convert.convert(Serializable.class, br));
            }
            return setk;
        }
        return new HashSet<K>(0);
    }


    public Collection<V> values(K matchKey) {
        this.matchKey = matchKey;
        return values();
    }

    @Override
    public Collection<V> values() {
        if (matchKey != null) {
            String mk = matchKey.toString();
            List<String> list = jedis.brpop(mk);
            List<V> lv = new ArrayList<>();
            for (String vb : list) {
                lv.add((V) ObjectUtil.deserialize(StrUtil.bytes(vb, "utf-8")));
            }
            return Collections.unmodifiableCollection(lv);
        }
        return null;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
