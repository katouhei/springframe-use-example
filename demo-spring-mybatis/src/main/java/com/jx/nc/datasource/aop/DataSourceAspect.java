package com.jx.nc.datasource.aop;

import com.jx.nc.datasource.DynamicDataSource;
import com.jx.nc.datasource.DynamicDataSourceContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @create 2018-02-23 10:57
 * 定义数据源的AOP切面，通过该Service的方法名判断是应该走主库还是从库
 */
@Component
@Aspect
public class DataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("execution(* com.jx.nc.*.service.*.*(..))")
    public void execPoint(){}

    /**
     * 在进入Service方法之前执行
     *
     * @param point 切面对象
     */
    @Before("execPoint()")
    public void before(JoinPoint point) {
//        if (TransactionSynchronizationManager.isActualTransactionActive())
//            return;
        // 获取到当前执行的方法名
        String methodName = point.getSignature().getName();
        logger.info("方法名："+methodName);
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
        return StringUtils.startsWithAny(methodName, "query", "find", "select", "get");
    }

}

