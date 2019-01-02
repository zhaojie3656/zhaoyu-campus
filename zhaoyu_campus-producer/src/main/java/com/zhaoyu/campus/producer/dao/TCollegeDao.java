package com.zhaoyu.campus.producer.dao;

import com.zhaoyu.campus.mysql.dao.BaseDao;
import com.zhaoyu.campus.producer.entity.TCollegeEntity;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TCollegeDao extends BaseDao <TCollegeEntity,TCollegeEntity,Integer> {
	
}
