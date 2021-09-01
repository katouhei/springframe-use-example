package com.jx.nc.shiro.filter;

import cn.hutool.core.util.StrUtil;
import com.jx.nc.shiro.ShiroSession;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ShiroSessionFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        if (subject == null || subject.getSession() == null) {
            return true;
        }
        Session session = subject.getSession();
        if(session != null && session instanceof ShiroSession) {
            ShiroSession shiroSession = (ShiroSession)session;

            //当设置的状态为-1则退出
            if(StrUtil.equals(shiroSession.getStatus(), "-1")) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}
