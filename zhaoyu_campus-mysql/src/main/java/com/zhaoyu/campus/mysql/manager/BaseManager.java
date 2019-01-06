package com.zhaoyu.campus.mysql.manager;

import java.util.List;

public interface BaseManager<T, P, ID> {

    public int insert(T object);

    public int insertNotNull(T object);

    public int insertBatch(List<T> list);

    public int updateById(T t, ID id);
    
    public int update(T t, T c);

    public int delete(T object);

    public int deleteById(ID id);

    public int deleteByIds(List<ID> ids);

    public T getById(ID id);

    public List<T> getByIds(List<ID> ids);

    public List<T> select(P object);

    public int selectCount(P object);

}
