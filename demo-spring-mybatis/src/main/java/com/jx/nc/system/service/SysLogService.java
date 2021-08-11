package com.jx.nc.system.service;

import com.jx.nc.system.bean.SysLog;
import com.jx.nc.system.mapper.SysLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysLogService {

    private static final Logger logger = LoggerFactory.getLogger(SysLogService.class);

    @Resource
    private SysLogMapper sysLogMapper;

    /**
     * 新增日志信息
     *
     * @param sysLog
     * @return
     * @throws Exception
     */
    public boolean add(SysLog sysLog) throws Exception {
        int count = sysLogMapper.insert(sysLog);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 新增日志信息
     *
     * @param sysLog
     * @return
     * @throws Exception
     */
    public boolean update(SysLog sysLog) throws Exception {
        int count = sysLogMapper.updateById(sysLog);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public void allexec(SysLog log) throws Exception {
        System.out.println("更新结果：" + update(log));
        System.out.println("新增结果：" + add(log));
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    public SysLog getById(Object id) throws Exception {
        return sysLogMapper.selectById(id);
    }

    /**
     * 根据条件查询日志信息
     *
     * @param params
     * @return
     * @throws Exception
     */
    public List<SysLog> select(Map params) throws Exception {
        return sysLogMapper.selectByMap(params);
    }
}
