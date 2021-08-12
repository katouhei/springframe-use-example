package com.jx.nc.system.impl;

import com.jx.nc.system.bean.SysLog;
import com.jx.nc.system.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysLogServiceImpl {

    @Autowired
    private SysLogService sysLogService;

    public boolean add(SysLog sysLog) throws Exception {
        return sysLogService.add(sysLog);
    }

    public List<SysLog> select(Map params) throws Exception {
        return sysLogService.select(params);
    }
}
