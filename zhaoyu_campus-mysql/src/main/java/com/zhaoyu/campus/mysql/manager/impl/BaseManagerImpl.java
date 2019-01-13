package com.zhaoyu.campus.mysql.manager.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.zhaoyu.campus.mysql.dao.BaseDao;
import com.zhaoyu.campus.mysql.manager.BaseManager;


public class BaseManagerImpl<T, P, ID> implements BaseManager<T, P, ID> {

    private static final Logger logger = LoggerFactory.getLogger(BaseManagerImpl.class);

    @Autowired(required=false)
    private BaseDao<T, P, ID> baseDao;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    //	@Autowired
    //	private JdbcTemplate jdbcTemplate;

    @Override
    public int insert(T object) {
        return this.baseDao.insert(object);
    }

    @Override
    public int insertNotNull(T object) {
        return this.baseDao.insertNotNull(object);
    }

    @Override
    public int insertBatch(List<T> list) {
        return this.baseDao.insertBatch(list);
    }

    @SuppressWarnings("rawtypes")
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int insertBatch(List<T> list, Class daoClass) {

        Long currentTime = System.currentTimeMillis();

        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);

        sqlSession.insert(daoClass.getName() + ".insertBatch", list);
        sqlSession.commit();
        sqlSession.close();

        logger.info("insertBatch sqlSession insert : " + (System.currentTimeMillis() - currentTime));
        /*
         * 
        HashMap<String,List<T>> param = new HashMap<>();
        param.put("list", list);
        
        currentTime = System.currentTimeMillis();
        String sql = SqlHelper.getMapperSql(sqlSession, daoClass, "insertBatch", param);
        logger.info("insertBatch get sql : " + (System.currentTimeMillis()-currentTime));
        sqlSession.close();
        
        //logger.info("insertBatch sql: " + sql);
        
        currentTime = System.currentTimeMillis();			
        jdbcTemplate.execute(sql);		
        logger.info("insertBatch jdbcTemplate insert : " + (System.currentTimeMillis()-currentTime));
        */
        return 1;
    }

    @Override
    public int updateById(T t, ID id) {
        return this.baseDao.updateById(t, id);
    }

    @Override
    public int delete(T object) {
        return this.baseDao.delete(object);
    }

    @Override
    public int deleteById(ID id) {
        return this.baseDao.deleteById(id);
    }

    @Override
    public int deleteByIds(List<ID> list) {
        return this.baseDao.deleteByIds(list);
    }

    @Override
    public List<T> select(P object, LinkedHashMap<String, String> sort, Integer offset, Integer limit) {
        return this.baseDao.select(object, sort, offset, limit);
    }

    @Override
    public List<T> select(P object, LinkedHashMap<String, String> sort) {
        return this.baseDao.select(object, sort);
    }

    @Override
    public List<T> select(P object, Integer offset, Integer limit) {
        return this.baseDao.select(object, null, offset, limit);
    }

    @Override
    public List<T> select(P object) {
        return this.baseDao.select(object, null, null, null);
    }

    @Override
    public int selectCount(P object) {
        return this.baseDao.selectCount(object);
    }

    @Override
    public T getById(ID id) {
        return this.baseDao.getById(id);
    }

    @Override
    public List<T> getByIds(List<ID> ids) {
        return this.baseDao.getByIds(ids);
    }

    @Override
    public List<T> selectElse(T object, LinkedHashMap<String, String> sort, Integer offset, Integer limit) {
        return this.baseDao.selectElse(object, sort, offset, limit);
    }

    @Override
    public List<T> selectElse(T object, Integer offset, Integer limit) {
        return this.baseDao.selectElse(object, null, offset, limit);
    }

    @Override
    public List<T> selectElse(T object, LinkedHashMap<String, String> sort) {
        return this.baseDao.selectElse(object, sort, null, null);
    }

    @Override
    public int selectCountElse(T object) {
        return this.baseDao.selectCountElse(object);
    }
}
