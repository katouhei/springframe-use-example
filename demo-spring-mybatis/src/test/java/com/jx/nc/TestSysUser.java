package com.jx.nc;


import cn.hutool.json.JSONUtil;
import com.jx.nc.pkgenerate.redis.StandaloneRedisService;
import com.jx.nc.system.bean.SysUser;
import com.jx.nc.system.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml", "classpath:spring-standalone-redis.xml", "classpath:spring-pkgenerate-redis.xml"})
public class TestSysUser {

    private static final Logger logger = LoggerFactory.getLogger(TestSysUser.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private StandaloneRedisService standaloneRedisService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Test
    public void test1() throws Exception {
        SysUser user = new SysUser();
//        user.setUserId(standaloneRedisService.generatorDefaultId("USER"));
//        user.setUserName(standaloneRedisService.generatorDefaultId("USER"));
//        logger.info("新增结果：" + sysUserService.insert(user));
        List<SysUser> list = sysUserService.selectByMap(new HashMap());
        logger.info("查询结果：" + JSONUtil.toJsonStr(list));
    }
}
