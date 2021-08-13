package com.jx.nc;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring-db.xml", "classpath:spring-standalone-redis.xml", "classpath:spring-pkgenerate-redis.xml"})
public class DemoSysLog {

//    private static final Logger logger = LoggerFactory.getLogger(DemoSysLog.class);
//
//    @Autowired
//    private SysLogService sysLogService;
//
//    @Autowired
//    private StandaloneRedisService standaloneRedisService;
//
//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//
//    @Test
//    public void test1() throws Exception {
////        SysLog log = new SysLog();
////        log.setLogId(standaloneRedisService.generatorDefaultId("LOG"));
//////        log.setLogId("LOG202108110000005");
////        log.setLogInfo("测试新增日志"+LocalDateTime.now().format(FORMATTER));
////        log.setCreateTime(LocalDateTime.now());
//////        System.out.println("更新结果：" + sysLogService.update(log));
//////        System.out.println("新增结果：" + sysLogService.add(log));
////        sysLogService.allexec(log);
//
//        List<SysLog> list = sysLogService.select(new HashMap());
//        System.out.println("查询结果：" + JSONUtil.toJsonStr(list));
//
//        logger.info(JSONUtil.toJsonStr(list));
//    }
}
