package com.jx.nc.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.jx.nc.redis.StandaloneRedisService;
import com.jx.nc.system.bean.SysLog;
import com.jx.nc.system.bean.SysUser;
import com.jx.nc.system.service.SysLogService;
import com.jx.nc.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "API对外接口")
@Controller
@RequestMapping("/")
public class ApiController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private StandaloneRedisService standaloneRedisService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @PostMapping("/addInfo.api")
    @ApiOperation("新增用户信息")
    @ResponseBody
    public String addUser() throws Exception {
        SysUser user = new SysUser();
        user.setUserName("新增用户" + LocalDateTime.now().format(FORMATTER));
        Map resultMap = new HashMap<>();
        if (sysUserService.insert(user)) {
            resultMap.put("msg", "新增用户成功");
        } else {
            resultMap.put("msg", "新增用户失败");
        }
        return JSONUtil.toJsonStr(resultMap);
    }

    @PostMapping("/queryLogInfo.api")
    @ApiOperation("查询日志信息")
    @ResponseBody
    public String queryLogInfo() throws Exception {
        List<SysLog> list = sysLogService.select(new HashMap());
        return JSONUtil.toJsonStr(list);
    }

    @PostMapping("/queryInfo.api")
    @ApiOperation("查询用户信息")
    @ResponseBody
    public String queryInfo() throws Exception {
        List<SysUser> list = sysUserService.selectByMap(new HashMap());
        return JSONUtil.toJsonStr(list);
    }

    @PostMapping("/test.api")
    @ApiOperation("测试redis信息")
    @ResponseBody
    public String test() throws Exception {

        return RandomUtil.randomString(32);
    }
}
