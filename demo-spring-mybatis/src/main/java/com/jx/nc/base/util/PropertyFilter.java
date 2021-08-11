/**
 * Copyright (c) 2005-20010 springside.org.cn
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * <p>
 * $Id: PropertyFilter.java 1205 2010-09-09 15:12:17Z calvinxiu $
 */
package com.jx.nc.base.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 与具体ORM实现无关的属性过滤条件封装类, 主要记录页面中简单的搜索过滤条件.
 * filter_EQS_***
 *
 * @author calvin
 */
public class PropertyFilter {

    /**
     * 多个属性间OR关系的分隔符.
     */
    public static final String OR_SEPARATOR = "_OR_";

    /**
     * 属性比较类型.
     */
    public enum MatchType {
        EQ, LIKE, LT, GT, LE, GE, IN;
    }

    /**
     * 属性数据类型.
     */
    public enum PropertyType {
        S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class);

        private Class<?> clazz;

        private PropertyType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Class<?> getValue() {
            return clazz;
        }
    }

    private MatchType matchType = null;
    private Object matchValue = null;

    private Class<?> propertyClass = null;
    private String[] propertyNames = null;

    public PropertyFilter() {
    }

    /**
     * @param filterName 比较属性字符串,含待比较的比较类型、属性值类型及属性列表.
     *                   eg. LIKES_NAME_OR_LOGIN_NAME
     * @param value      待比较的值.
     */
    public PropertyFilter(final String filterName, final String value) {

        String firstPart = StringUtils.substringBefore(filterName, "_");
        String matchTypeCode = StringUtils.substring(firstPart, 0, firstPart.length() - 1);
        String propertyTypeCode = StringUtils.substring(firstPart, firstPart.length() - 1, firstPart.length());

        try {
            matchType = Enum.valueOf(MatchType.class, matchTypeCode);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性比较类型.", e);
        }

        try {
            propertyClass = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();

        } catch (RuntimeException e) {
            throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性值类型.", e);
        }

        String propertyNameStr = StringUtils.substringAfter(filterName, "_");
        Assert.isTrue(StringUtils.isNotBlank(propertyNameStr), "filter名称" + filterName + "没有按规则编写,无法得到属性名称.");
        propertyNames = StringUtils.splitByWholeSeparator(propertyNameStr, PropertyFilter.OR_SEPARATOR);

        if (MatchType.IN.equals(this.matchType)) {
            this.matchValue = value;
        } else {
            this.matchValue = ConvertUtils.convertStringToObject(value, propertyClass);
        }
    }

    /**
     * @param filterName 比较属性字符串,含待比较的比较类型、属性值类型及属性列表.
     *                   eg. LIKES_NAME_OR_LOGIN_NAME
     * @param value      待比较的值.
     */
    public PropertyFilter(final String filterName, final Object value) {

        String firstPart = StringUtils.substringBefore(filterName, "_");
        String matchTypeCode = StringUtils.substring(firstPart, 0, firstPart.length() - 1);
        String propertyTypeCode = StringUtils.substring(firstPart, firstPart.length() - 1, firstPart.length());

        try {
            matchType = Enum.valueOf(MatchType.class, matchTypeCode);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性比较类型.", e);
        }

        try {
            propertyClass = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();

        } catch (RuntimeException e) {
            throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性值类型.", e);
        }

        String propertyNameStr = StringUtils.substringAfter(filterName, "_");
        Assert.isTrue(StringUtils.isNotBlank(propertyNameStr), "filter名称" + filterName + "没有按规则编写,无法得到属性名称.");
        propertyNames = StringUtils.splitByWholeSeparator(propertyNameStr, PropertyFilter.OR_SEPARATOR);

        this.matchValue = value;
    }

    /**
     * 从HttpRequest中创建PropertyFilter列表, 默认Filter属性名前缀为filter.
     * <p>
     * see: #buildFromHttpRequest(HttpServletRequest, String)
     */
    public static List<PropertyFilter> buildFromHttpRequest(final Map<String, Object> data) {
        return buildFromHttpRequest(data, "filter");
    }

    /**
     * 从HttpRequest中创建PropertyFilter列表
     * PropertyFilter命名规则为Filter属性前缀_比较类型属性类型_属性名.
     * <p>
     * eg.
     * filter_EQS_name
     * filter_LIKES_name_OR_email
     */
    public static List<PropertyFilter> buildFromHttpRequest(final Map<String, Object> data, final String filterPrefix) {
        List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();

        //从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> filterParamMap = getParametersStartingWith(data, filterPrefix + "_");

        //分析参数Map,构造PropertyFilter列表
        for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
            String filterName = entry.getKey();
            Object tempVal = entry.getValue();

            String value = null;
            if (tempVal instanceof Integer) {
                Integer temInteger = (Integer) tempVal;
                value = temInteger.toString();
            } else if (tempVal instanceof Long) {
                Long temInteger = (Long) tempVal;
                value = temInteger.toString();
            } else if (tempVal instanceof ArrayList) {

            } else {
                value = (String) entry.getValue();
            }

            //如果value值为空,则忽略此filter.
            if (StringUtils.isNotBlank(value)) {
                PropertyFilter filter = new PropertyFilter(filterName, value);
                if (!filter.matchType.equals(MatchType.IN) && filter.propertyClass.equals(Long.class) && !NumberUtils.isCreatable(value)) {
                    continue;
                } else if (!filter.matchType.equals(MatchType.IN) && filter.propertyClass.equals(Integer.class) && !NumberUtils.isCreatable(value)) {
                    continue;
                }
                filterList.add(filter);
            } else if (tempVal instanceof ArrayList) {
                PropertyFilter filter = new PropertyFilter(filterName, tempVal);
                filterList.add(filter);
            }

        }
        return filterList;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getParametersStartingWith(Map<String, Object> data, String prefix) {
        Assert.notNull(data, "Request must not be null");
        Map<String, Object> params = new TreeMap<String, Object>();
        if (prefix == null) {
            prefix = "";
        }
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();

            if ("".equals(prefix) || key.startsWith(prefix)) {
                String unprefixed = key.substring(prefix.length());

                Object tempVal = entry.getValue();
                if (tempVal instanceof String) {
                    String val = (String) entry.getValue();
                    String values = val;
                    if (StringUtils.isBlank(values)) {
                        // Do nothing, no values found at all.
                    }
//					else if (values.length > 1) {
//						params.put(unprefixed, values);//多选框未处理
//					}
                    else {
                        params.put(unprefixed, values);
                    }
                } else if (null == tempVal) {
                    Assert.notNull(tempVal, "value must not be null");
                } else {
                    params.put(unprefixed, tempVal);
                }
            }
        }
        return params;
    }

    /**
     * 获取比较值的类型.
     */
    public Class<?> getPropertyClass() {
        return propertyClass;
    }

    /**
     * 获取比较方式.
     */
    public MatchType getMatchType() {
        return matchType;
    }

    /**
     * 获取比较值.
     */
    public Object getMatchValue() {
        return matchValue;
    }

    /**
     * 获取比较属性名称列表.
     */
    public String[] getPropertyNames() {
        return propertyNames;
    }

    /**
     * 获取唯一的比较属性名称.
     */
    public String getPropertyName() {
        Assert.isTrue(propertyNames.length == 1, "There are not only one property in this filter.");
        return propertyNames[0];
    }

    /**
     * 是否比较多个属性.
     */
    public boolean hasMultiProperties() {
        return (propertyNames.length > 1);
    }

}
