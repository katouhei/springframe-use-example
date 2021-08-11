package com.jx.nc.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public abstract class TemplateService<T> {

    protected abstract TemplateMapper<T> mapper();

    /**
     * 新增对象
     *
     * @param entity
     * @return
     * @throws Exception
     */
    public boolean insert(T entity) throws Exception {
        int count = mapper().insert(entity);
        if(count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 不存在新增，存在则更新
     *
     * @param entity
     * @return
     * @throws Exception
     */
    public boolean saveOrUpdate(T entity) throws Exception {
        int count =  mapper().saveOrUpdate(entity);
        if(count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 更新对象
     *
     * @param entity
     * @return
     * @throws Exception
     */
    public boolean updateById(T entity) throws Exception {
        int count =  mapper().updateById(entity);
        if(count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    public boolean deleteById(Serializable id) throws Exception {
        int count =  mapper().deleteById(id);
        if(count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除对象
     *
     * @param params
     * @return
     * @throws Exception
     */
    public boolean deleteByMap(Map params) throws Exception {
        int count =  mapper().deleteByMap(params);
        if(count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    public T selectById(Serializable id) throws Exception {
        return mapper().selectById(id);
    }

    /**
     * 查询对象结果集
     *
     * @param params
     * @return
     * @throws Exception
     */
    public List<T> selectByMap(Map params) throws Exception {
        return mapper().selectByMap(params);
    }
}
