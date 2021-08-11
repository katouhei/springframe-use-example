package com.jx.nc.system.controller;

import cn.hutool.json.JSONUtil;
import com.jx.nc.pkgenerate.redis.StandaloneRedisService;
import com.jx.nc.system.bean.SysLog;
import com.jx.nc.system.service.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Controller
public class SysLogController {

    private static final Logger logger = LoggerFactory.getLogger(SysLogController.class);

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private StandaloneRedisService standaloneRedisService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @RequestMapping("/syslog/save")
    @ResponseBody
    public String save() throws Exception {
        SysLog log = new SysLog();
        log.setLogId(standaloneRedisService.generatorDefaultId("LOG"));
//        log.setLogId("LOG202108110000005");
        log.setLogInfo("测试新增日志"+ LocalDateTime.now().format(FORMATTER));
        log.setCreateTime(LocalDateTime.now());
        if(sysLogService.add(log)) {
            return "新增日志成功";
        }
        return "新增日志失败";

    }

    @RequestMapping("/syslog/query")
    @ResponseBody
    public String query() throws Exception {
        List<SysLog> list = sysLogService.select(new HashMap());
        String result = JSONUtil.toJsonStr(list);
        logger.info("查询结果：" + result);
        return result;

    }
}
