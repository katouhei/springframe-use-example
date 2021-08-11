package com.jx.nc.pkgenerate.redis;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;


public class SingleRedisService {

    private Logger log = LoggerFactory.getLogger(SingleRedisService.class);

    @Autowired
    private Jedis jedis;

    public void setSeq(Integer seq) {
        jedis.set("seq", String.valueOf(seq));
    }

    public Integer getSeq() {
        String seq = jedis.get("seq");
        if (StrUtil.isNotEmpty(seq) && NumberUtil.isInteger(seq)) {
            return NumberUtil.parseInt(seq) + 1;
        } else {
            return 1;
        }
    }

}
