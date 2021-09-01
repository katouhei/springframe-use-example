package com.jx.nc;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;


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

    @Test
    public void test2() {
        String value = "rO0ABXNyABxjb20uangubmMuc2hpcm8uU2hpcm9TZXNzaW9uW0X2itT2DYcCAAFMAAZzdGF0dXN0ABJMamF2YS9sYW5nL1N0cmluZzt4cgAqb3JnLmFwYWNoZS5zaGlyby5zZXNzaW9uLm1ndC5TaW1wbGVTZXNzaW9unRyhuNWMYm4DAAB4cHcCAFt0ACQ5ZGI0ZWVmOC05Y2RiLTRjYTItOGU5MS1iNDY4M2IzYjBkMGJzcgAOamF2YS51dGlsLkRhdGVoaoEBS1l0GQMAAHhwdwgAAAF7oL7cl3hxAH4ABncTAAAAAAAbd0AACTEyNy4wLjAuMXhw";
        Deque<Serializable> deque = new ArrayDeque<Serializable>();
        deque.add("abc");
        System.out.println(StrUtil.toString(value));
        String s = StrUtil.toString(deque);
        Deque<Serializable> d = (Deque<Serializable>)Convert.convert(Object.class, s);
        System.out.println(d.size());


    }
}
