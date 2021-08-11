/**
 * Copyright (c) 2005-2010 springside.org.cn
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * <p>
 * $Id: ConvertUtils.java 1211 2010-09-10 16:20:45Z calvinxiu $
 */
package com.jx.nc.base.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class ConvertUtils {


    /**
     * 提取集合中的对象的属性(通过getter函数), 组合成List.
     *
     * @param collection 来源集合.
     * @param propertyName 要提取的属性名.
     */
    @SuppressWarnings("unchecked")
    public static List convertElementPropertyToList(final Collection collection, final String propertyName) throws Exception {
        List list = new ArrayList();

        for (Object obj : collection) {
            list.add(PropertyUtils.getProperty(obj, propertyName));
        }

        return list;
    }

    /**
     * 提取集合中的对象的属性(通过getter函数), 组合成由分割符分隔的字符串.
     *
     * @param collection 来源集合.
     * @param propertyName 要提取的属性名.
     * @param separator 分隔符.
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static String convertElementPropertyToString(final Collection collection, final String propertyName,
                                                        final String separator) throws Exception {
        List list = convertElementPropertyToList(collection, propertyName);
        return StringUtils.join(list, separator);
    }

    /**
     * 转换字符串到相应类型.
     *
     * @param value 待转换的字符串.
     * @param toType 转换目标类型.
     */
    public static Object convertStringToObject(String value, Class<?> toType) {
        //使用CovertUtils注册创建一个日期格式转换器
        org.apache.commons.beanutils.ConvertUtils.register(new DateLocaleConverter(), Date.class);
        //处理时间格式
        DateConverter dateConverter = new DateConverter();
        //设置日期格式
        dateConverter.setPatterns(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
        //注册格式
        org.apache.commons.beanutils.ConvertUtils.register(dateConverter, Date.class);
        return org.apache.commons.beanutils.ConvertUtils.convert(value, toType);
    }
}
