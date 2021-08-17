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
import java.util.Map;

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
        log.setLogInfo("测试新增日志" + LocalDateTime.now().format(FORMATTER));
        log.setCreateTime(LocalDateTime.now());
        Map resultMap = new HashMap<>();
        if (sysLogService.add(log)) {
            resultMap.put("msg", "新增日志成功");
        } else {
            resultMap.put("msg", "新增日志失败");
        }
        return JSONUtil.toJsonStr(resultMap);

    }

    @RequestMapping("/syslog/replaceUpdate")
    @ResponseBody
    public String replaceUpdate() throws Exception {
        SysLog log = new SysLog();
        log.setLogId(standaloneRedisService.generatorDefaultId("LOG"));
//        log.setLogId("LOG202108120000007");
        log.setLogInfo("测试新增或更新日志" + LocalDateTime.now().format(FORMATTER));
        log.setCreateTime(LocalDateTime.now());
        Map resultMap = new HashMap<>();
        sysLogService.replaceUpdate(log);
        resultMap.put("msg", "执行完成");
        return JSONUtil.toJsonStr(resultMap);
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
