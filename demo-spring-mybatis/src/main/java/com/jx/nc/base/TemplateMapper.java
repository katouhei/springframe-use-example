package com.jx.nc.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface TemplateMapper<T> {


    /**
     * 映射XML中新增对象SQL文
     * @param entity
     * @return
     * @throws Exception
     */
    int insert(T entity) throws Exception;

    /**
     * 映射XML中新增或更新对象SQL文
     * @param entity
     * @return
     * @throws Exception
     */
    int saveOrUpdate(T entity) throws Exception;

    /**
     * 映射XML中根据主键更新对象
     * @param entity
     * @return
     * @throws Exception
     */
    int updateById(T entity) throws Exception;

    /**
     * 映射XML中根据主键删除
     * @param id
     * @return
     * @throws Exception
     */
    int deleteById(Object id) throws Exception;

    /**
     * 映射XML中根据条件查询
     * @param params
     * @return
     * @throws Exception
     */
    int deleteByMap(Map params) throws Exception;

    /**
     * 映射XML中根据主键查询对象
     * @param id
     * @return
     * @throws Exception
     */
    T selectById(Object id) throws Exception;

    /**
     * 映射XML中查询对象集合SQL
     * @param params
     * @return
     * @throws Exception
     */
    List<T> selectByMap(Map params) throws Exception;
}
