package com.jx.nc.sys.service.impl;

import com.jx.nc.sys.entity.SysLog;
import com.jx.nc.sys.mapper.SysLogMapper;
import com.jx.nc.sys.service.ISysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志信息 服务实现类
 * </p>
 *
 * @author katouhei
 * @since 2021-08-13
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {


    public void replaceUpdate(SysLog log) throws Exception {
        System.out.println("更新结果：" + updateById(log));
        System.out.println("新增结果：" + save(log));
    }
}
