package com.jx.nc.base;

import com.jx.nc.base.util.ConvertUtils;
import com.jx.nc.base.util.PropertyFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLProvider {

    private static final Logger logger = LoggerFactory.getLogger(SQLProvider.class);


    /**
     * 配置查询MAP的SQL
     *
     * @param condMap
     * @return
     */
    protected SQL configWhereSortSQL(String sqlselect, Map condMap) {
        final String selectSQL = sqlselect.substring(sqlselect.toLowerCase().indexOf("select") + 6);
        return new SQL() {{
            SELECT(selectSQL);
            List<String> whereCondList = translateWhereParams(condMap);
            for (String whereCond : whereCondList) {
                WHERE(whereCond);
            }
            if (condMap.get("groupByColumn") != null) {
                String orderByColumn = String.valueOf(condMap.get("groupByColumn"));
                GROUP_BY(charTool(orderByColumn));
            }
            if (condMap.get("orderByColumn") != null) {
                String orderByColumn = String.valueOf(condMap.get("orderByColumn"));
                ORDER_BY(charTool(orderByColumn));
            }
        }};
    }

    /**
     * 配置查询条件
     *
     * @param condMap
     * @return
     */
    protected List<String> translateWhereParams(Map condMap) {
        List<String> whereCondList = new ArrayList<String>();
        //转换
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(condMap);
        for (PropertyFilter por : filters) {
            PropertyFilter.MatchType matchType = por.getMatchType();
            switch (matchType) {
                case EQ:
                    whereCondList.add(buildCondSql(por, "=", condMap));
                    break;
                case LIKE:
                    whereCondList.add(buildCondSql(por, "like", condMap));
                    break;
                case LE:
                    whereCondList.add(buildCondSql(por, "<=", condMap));
                    break;
                case LT:
                    whereCondList.add(buildCondSql(por, "<", condMap));
                    break;
                case GE:
                    whereCondList.add(buildCondSql(por, ">=", condMap));
                    break;
                case GT:
                    whereCondList.add(buildCondSql(por, ">", condMap));
                    break;
                case IN:
                    StringBuffer buf = new StringBuffer();
                    //处理字段的值
                    String matchValue = String.valueOf(por.getMatchValue());
                    String matchValue2 = StringUtils.replace(matchValue, " ", "");
                    String[] values = StringUtils.split(matchValue2, ",");
                    if (null == values || values.length == 0) {
                        throw new IllegalArgumentException("filter[" + por.getPropertyName() + "]的值没有按规则编写,无法得到它的值.");
                    }
                    String mapkey = por.getPropertyName().replace(".", "");
                    for (int i = 0; i < values.length; i++) {
                        String tempValue = values[i];
                        values[i] = String.format(" #{%s}", mapkey + i);
                        condMap.put(mapkey + i, ConvertUtils.convertStringToObject(tempValue,
                                por.getPropertyClass()));
                    }
                    String values2 = StringUtils.join(values, ",");
                    buf.append(" in (").append(values2).append(")");
                    whereCondList.add(charTool(por.getPropertyName()) + buf);
                    break;
            }
        }
        return whereCondList;
    }

    private String buildCondSql(PropertyFilter filter, String operator, Map condMap) {
        StringBuffer condSql = new StringBuffer();
        if (filter.hasMultiProperties()) {
            condSql.append("(");
            for (int i = 0; i < filter.getPropertyNames().length; i++) {
                String propertyName = filter.getPropertyNames()[i];
                String mapkey = propertyName.replace(".", "");
                if (i != 0) {
                    condSql.append(" OR ");
                }
                condSql.append(charTool(propertyName)).append(" " + operator);
                if ("like".equals(operator)) {
                    condSql.append(" concat('%', ").append(String.format("#{%s}", mapkey + i)).append(", '%') ");
                } else {
                    condSql.append(String.format(" #{%s}", mapkey + i));
                }
                condMap.put(mapkey + i, filter.getMatchValue());
            }
            condSql.append(")");
        } else {
            String mapkey = filter.getPropertyName().replace(".", "");
            condSql.append(charTool(filter.getPropertyName())).append(" " + operator);
            if ("like".equals(operator)) {
                condSql.append(" concat('%', ").append(String.format("#{%s}", mapkey)).append(", '%') ");
            } else {
                condSql.append(String.format(" #{%s}", mapkey));
            }
            condMap.put(mapkey, filter.getMatchValue());
        }
        return condSql.toString();
    }

    /**
     * 把VO中的字段转成数据库对应字段，如：userName -> user_name
     * seek
     * 2016-3-23  下午05:23:57
     *
     * @param s
     * @return
     */
    public static String charTool(String s) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c)) {
                str.append("_").append(Character.toLowerCase(c));
            } else {
                str.append(c);
            }
        }
        return str.toString();
    }
}
