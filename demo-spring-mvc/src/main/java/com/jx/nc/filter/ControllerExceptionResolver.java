package com.jx.nc.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ControllerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        e.printStackTrace();
        System.out.println(DateUtil.formatDateTime(new Date()) + "操作异常，请联系管理员");
        try {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            Map resultMap = new HashMap<>();
            resultMap.put("msg", DateUtil.formatDateTime(new Date()) + "操作异常，请联系管理员");
            httpServletResponse.getWriter().write(JSONUtil.toJsonStr(resultMap));
            httpServletResponse.getWriter().flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }
}
