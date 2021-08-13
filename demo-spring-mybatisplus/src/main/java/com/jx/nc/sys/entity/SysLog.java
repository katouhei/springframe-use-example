package com.jx.nc.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 日志信息
 * </p>
 *
 * @author katouhei
 * @since 2021-08-13
 */
public class SysLog implements Serializable {


    /**
     * 日志ID
     */
    @TableId("log_id")
    private String logId;

    /**
     * 日志信息
     */
    private String logInfo;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

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
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysLog{" +
            "logId=" + logId +
            ", logInfo=" + logInfo +
            ", createTime=" + createTime +
        "}";
    }
}
