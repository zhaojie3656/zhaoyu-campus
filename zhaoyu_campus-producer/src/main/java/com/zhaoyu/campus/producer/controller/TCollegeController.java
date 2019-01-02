package com.zhaoyu.campus.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoyu.campus.common.utils.ServiceResult;
import com.zhaoyu.campus.producer.entity.TCollegeEntity;
import com.zhaoyu.campus.producer.manager.TCollegeManager;

@RestController
@RequestMapping("/college")
public class TCollegeController {
	
	@Autowired
	TCollegeManager tCollegeManager;
	
	@RequestMapping("/getById")
	public ServiceResult getById(@RequestParam Integer id) {
		TCollegeEntity entity = tCollegeManager.getById(id);
		return ServiceResult.ok(entity);
    }

}
