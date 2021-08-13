package com.jx.nc.sys.service;

import com.jx.nc.sys.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 日志信息 服务类
 * </p>
 *
 * @author katouhei
 * @since 2021-08-13
 */
public interface ISysLogService extends IService<SysLog> {

    void replaceUpdate(SysLog log) throws Exception;
}
