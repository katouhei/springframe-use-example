package com.jx.nc;

import cn.hutool.json.JSONUtil;
import com.jx.nc.redis.StandaloneRedisService;
import com.jx.nc.system.bean.SysLog;
import com.jx.nc.system.service.SysLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;


@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml", "classpath:spring-standalone-redis.xml", "classpath:spring-pkgenerate-redis.xml"})
public class TestSysLog {

    private static final Logger logger = LoggerFactory.getLogger(TestSysLog.class);

    @Resource
    private SysLogService sysLogService;

    @Autowired
    private StandaloneRedisService standaloneRedisService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Test
    public void test1() throws Exception {
//        SysLog log = new SysLog();
//        log.setLogId(standaloneRedisService.generatorDefaultId("LOG"));
////        log.setLogId("LOG202108110000005");
//        log.setLogInfo("测试新增日志"+LocalDateTime.now().format(FORMATTER));
//        log.setCreateTime(LocalDateTime.now());
////        System.out.println("更新结果：" + sysLogService.update(log));
////        System.out.println("新增结果：" + sysLogService.add(log));
//        sysLogService.replaceUpdate(log);

        List<SysLog> list = sysLogService.select(new HashMap());
        System.out.println("查询结果：" + JSONUtil.toJsonStr(list));

        logger.info(JSONUtil.toJsonStr(list));
    }
}
