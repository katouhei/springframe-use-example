package com.jx.nc.system.service;

import com.jx.nc.base.TemplateMapper;
import com.jx.nc.base.TemplateService;
import com.jx.nc.system.bean.SysUser;
import com.jx.nc.system.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class SysUserService extends TemplateService<SysUser> {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    protected TemplateMapper<SysUser> mapper() {
        return sysUserMapper;
    }
}
