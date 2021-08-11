package com.jx.nc.system.bean;

import java.io.Serializable;

/**
 * 日志信息
 * 类名:SysLog
 * 创建人:katouhei
 * 创建时间:20210810
 */
public class SysLog implements Serializable {

    
    /**
     * 日志ID
     */
    private String logId;
    
    /**
     * 日志信息
     */
    private String logInfo;
    
    /**
     * 创建时间
     */
    private java.time.LocalDateTime createTime;
    
    
    public String getLogId() {
		return logId;
	}
	
	public void setLogId(String logId) {
		this.logId = logId;
	}
    
    public String getLogInfo() {
		return logInfo;
	}
	
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
    
    public java.time.LocalDateTime getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(java.time.LocalDateTime createTime) {
		this.createTime = createTime;
	}
}