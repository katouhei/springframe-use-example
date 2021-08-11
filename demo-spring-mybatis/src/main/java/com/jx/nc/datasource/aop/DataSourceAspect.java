package com.jx.nc.datasource.aop;

import com.jx.nc.datasource.DynamicDataSourceContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;

/**
 * @create 2018-02-23 10:57
 * 定义数据源的AOP切面，通过该Service的方法名判断是应该走主库还是从库
 */
public class DataSourceAspect {

    /**
     * 在进入Service方法之前执行
     *
     * @param point 切面对象
     */
    public void before(JoinPoint point) {
        // 获取到当前执行的方法名
        String methodName = point.getSignature().getName();
        if (isSlave(methodName)) {
            // 标记为主库
            DynamicDataSourceContextHolder.setDataSourceType(DynamicDataSourceContextHolder.DB_MASTET);
        } else {
            // 标记为次库
            DynamicDataSourceContextHolder.setDataSourceType(DynamicDataSourceContextHolder.DB_SLAVE);
        }
    }

    /**
     * 判断是否为次库
     *
     * @param methodName
     * @return
     */
    private Boolean isSlave(String methodName) {
        // 方法名以query、find、get开头的方法名走从库
        return StringUtils.startsWithAny(methodName, "query", "find", "get");
    }

}

