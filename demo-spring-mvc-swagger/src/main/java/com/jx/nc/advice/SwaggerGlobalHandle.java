package com.jx.nc.advice;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.jx.nc.busexcep.CustomBusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 针对全局controller做的处理
 * 1、全局异常处理
 * 2、全局数据绑定
 * 3、全局数据预处理
 */
@ControllerAdvice
public class SwaggerGlobalHandle {

    private static final Logger logger = LoggerFactory.getLogger(SwaggerGlobalHandle.class);

    @ExceptionHandler(CustomBusException.class)
    @ResponseBody
    public String customBusException(CustomBusException e) {
        Map resultMap = new HashMap<>();
        resultMap.put("msg", DateUtil.formatDateTime(new Date()) + "业务异常" + e.getMessage());
        logger.info("CustomBusException捕获异常:" + e.getMessage());
        return JSONUtil.toJsonStr(resultMap);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String customException(Exception e) {
        Map resultMap = new HashMap<>();
        resultMap.put("msg", DateUtil.formatDateTime(new Date()) + "增强Controller捕获异常");
        logger.info("增强Controller捕获异常:" + e.getMessage());
        return JSONUtil.toJsonStr(resultMap);
    }
}
