package com.zhaoyu.campus.producer.manager.impl;

import org.springframework.stereotype.Service;

import com.zhaoyu.campus.mysql.manager.impl.BaseManagerImpl;
import com.zhaoyu.campus.producer.entity.TCollegeEntity;
import com.zhaoyu.campus.producer.manager.TCollegeManager;

@Service
public class TCollegeManagerImpl extends BaseManagerImpl <TCollegeEntity,TCollegeEntity,Integer> implements TCollegeManager{
	
}
