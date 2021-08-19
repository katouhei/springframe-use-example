package com.jx.nc.redis.queue;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;

import java.io.Serializable;
import java.util.Date;

public class ListenRedisQueueTask {
    public void handleMessage(Serializable content){

        System.out.println(DateUtil.formatDateTime(new Date())+"队列信息："+ JSONUtil.toJsonStr(content));
    }
}
