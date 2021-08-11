package com.jx.nc.pkgenerate;

public interface PkGenerator {
    /**
     * 生成下一个不重复的流水号
     * @return
     */
    String next();
}
