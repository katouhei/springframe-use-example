package com.jx.nc.idgenerate;

/**
 * ID生成器接口, 用于生成全局唯一的ID流水号
 */
public interface IdGenerator {
    /**
     * 生成下一个不重复的流水号
     * @return
     */
    String next();
}
