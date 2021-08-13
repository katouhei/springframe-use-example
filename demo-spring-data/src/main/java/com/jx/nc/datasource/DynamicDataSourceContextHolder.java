package com.jx.nc.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建类 DynamicDataSourceHolder,线程安全管理数据源
 */
public final class DynamicDataSourceContextHolder {

    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);
    /**
     * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
     * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();
    public static final String DB_MASTET = "master";
    public static final String DB_SLAVE = "slave";

    /**
     * 设置数据源的变量
     */
    public static void setDataSourceType(String dsType) {
        clearDataSourceType();
        logger.info("切换到{}数据源", dsType);
        CONTEXT_HOLDER.set(dsType);
    }

    /**
     * 获得数据源的变量
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清空数据源变量
     */
    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }

}
