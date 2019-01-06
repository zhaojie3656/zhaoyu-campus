package com.zhaoyu.campus.mysql.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @param <T>
 * @param <ID>
 */
public interface BaseDao<T, P, ID> {

    public T getById(ID id);

    public List<T> getByIds(List<ID> ids);

    public Integer selectCount(@Param("tb") P p);

    public List<T> select(@Param("tb") P p);

    public Integer insert(T t);

    public Integer insertNotNull(T t);

    public Integer insertBatch(List<T> list);

    public Integer updateById(@Param("tb") T t, @Param("id") ID id);
    
    public Integer update(@Param("tb") T t, @Param("c")T c);

    public Integer delete(T t);

    public Integer deleteById(ID id);

    public Integer deleteByIds(List<ID> list);

}