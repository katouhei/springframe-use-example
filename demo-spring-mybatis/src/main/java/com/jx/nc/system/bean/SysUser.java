package com.jx.nc.system.bean;

import java.io.Serializable;

/**
 * 用户信息
 * 类名:SysUser
 * 创建人:katouhei
 * 创建时间:20210810
 */
public class SysUser implements Serializable {

    
    /**
     * 账号ID
     */
    private Integer userId;
    
    /**
     * 用户姓名
     */
    private String userName;
    
    
    public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
    
    public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
}