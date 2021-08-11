package com.jx.nc.pkgenerate;

public class DefaultPkGeneratorConfig {

    //分隔符字符串
    private String splitString = "";
    //开始顺序
    private int initial = 1;
    //以字符串开头
    private String prefix = "";
    //获取滚动间隔, 单位: 秒
    private int rollingInterval = 1;
    //格式化字符长度
    private int formatLength = 6;
    //填充字符串
    private String fillString = "0";

    private String pattern = "yyyyMMddHHmm";

    public int getRollingInterval() {
        return rollingInterval;
    }

    public void setRollingInterval(int rollingInterval) {
        this.rollingInterval = rollingInterval;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getSplitString() {
        return splitString;
    }

    public void setSplitString(String splitString) {
        this.splitString = splitString;
    }

    public int getInitial() {
        return initial;
    }

    public void setInitial(int initial) {
        this.initial = initial;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getFormatLength() {
        return formatLength;
    }

    public void setFormatLength(int formatLength) {
        this.formatLength = formatLength;
    }

    public String getFillString() {
        return fillString;
    }

    public void setFillString(String fillString) {
        this.fillString = fillString;
    }
}
