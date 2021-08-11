package com.jx.nc.idgenerate;

public class DefaultIdGeneratorConfig implements IdGeneratorConfig {

    @Override
    public String getSplitString() {
        return "";
    }

    @Override
    public int getInitial() {
        return 1;
    }

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public int getRollingInterval() {
        return 1;
    }

    @Override
    public int getFormatLength() {
        return 7;
    }

    @Override
    public String getFillChar() {
        return "0";
    }
}
