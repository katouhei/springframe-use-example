package com.jx.nc;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jx.nc.redis.StandaloneRedisService;
import com.jx.nc.sys.service.ISysLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.format.DateTimeFormatter;


@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis-plus.xml", "classpath:spring-standalone-redis.xml", "classpath:spring-pkgenerate-redis.xml"})
public class TestplusSysLog {

    private static final Logger logger = LoggerFactory.getLogger(TestplusSysLog.class);

    @Autowired
    private ISysLogService sysLogService;

    @Autowired
    private StandaloneRedisService standaloneRedisService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Test
    public void test1() throws Exception {
//        SysLog log = sysLogService.getById("LOG202108120000007");
//        System.out.println("结果："+ JSONUtil.toJsonStr(log));
//
//        log = new SysLog();
////        log.setLogId(standaloneRedisService.generatorDefaultId("LOG"));
//        log.setLogId("LOG202108130000001");
//        log.setLogInfo("测试日志"+LocalDateTime.now().format(FORMATTER));
//        log.setCreateTime(LocalDateTime.now());
////        System.out.println("更新结果：" + sysLogService.update(log));
////        System.out.println("新增结果：" + sysLogService.add(log));
////        sysLogService.replaceUpdate(log);
//        sysLogService.updateById(log);
//        sysLogService.save(log);
//        List<SysLog> list = sysLogService.list();
//        System.out.println("查询结果：" + JSONUtil.toJsonStr(list));
//
//        logger.info(JSONUtil.toJsonStr(list));
        IPage page = new Page(1, 1);
        page = sysLogService.page(page);
        System.out.println("查询结果：" + JSONUtil.toJsonStr(page));

    }
}
