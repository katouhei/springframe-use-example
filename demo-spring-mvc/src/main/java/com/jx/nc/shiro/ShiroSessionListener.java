package com.jx.nc.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class ShiroSessionListener implements SessionListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ShiroSessionCacheDao shiroSessionDao;

    @Override
    public void onStart(Session session) {
        // 会话创建时触发
        logger.info("ShiroSessionListener session {} 被创建", session.getId());
    }

    @Override
    public void onStop(Session session) {
        // 会话被停止时触发
        shiroSessionDao.deleteSession(session.getId());
        logger.info("ShiroSessionListener session {} 被销毁", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        //会话过期时触发
        shiroSessionDao.deleteSession(session.getId());
        logger.info("ShiroSessionListener session {} 过期", session.getId());
    }

}
