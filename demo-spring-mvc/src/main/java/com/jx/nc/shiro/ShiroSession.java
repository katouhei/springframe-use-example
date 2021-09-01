package com.jx.nc.shiro;

import org.apache.shiro.session.mgt.SimpleSession;

import java.io.Serializable;

public class ShiroSession extends SimpleSession implements Serializable {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
