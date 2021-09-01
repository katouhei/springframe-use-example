package com.jx.nc.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;

public class ShiroSessionDao extends AbstractSessionDAO {

    private ShiroSessionCacheDao shiroSessionCacheDao;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        shiroSessionCacheDao.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        return shiroSessionCacheDao.getSession(serializable);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        shiroSessionCacheDao.saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null) {
            return;
        }
        Serializable id = session.getId();
        if (id != null) {
            shiroSessionCacheDao.deleteSession(id);
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return shiroSessionCacheDao.getAllSessions();
    }

    public void setShiroSessionCacheDao(ShiroSessionCacheDao shiroSessionCacheDao) {
        this.shiroSessionCacheDao = shiroSessionCacheDao;
    }
}
