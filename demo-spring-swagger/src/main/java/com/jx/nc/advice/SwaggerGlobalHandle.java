package com.jx.nc.advice;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.jx.nc.system.controller.SysLogController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class SwaggerGlobalHandle {

    private static final Logger logger = LoggerFactory.getLogger(SwaggerGlobalHandle.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String customException(Exception e) {
        Map resultMap = new HashMap<>();
        resultMap.put("msg", DateUtil.formatDateTime(new Date()) + "增强Controller捕获异常");
        logger.info("增强Controller捕获异常:"+e.getMessage());
        return JSONUtil.toJsonStr(resultMap);
    }
}
