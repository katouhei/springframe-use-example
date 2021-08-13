package com.jx.nc.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author katouhei
 * @since 2021-08-13
 */
public class SysUser implements Serializable {


    /**
     * 账号ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
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

    @Override
    public String toString() {
        return "SysUser{" +
            "userId=" + userId +
            ", userName=" + userName +
        "}";
    }
}
