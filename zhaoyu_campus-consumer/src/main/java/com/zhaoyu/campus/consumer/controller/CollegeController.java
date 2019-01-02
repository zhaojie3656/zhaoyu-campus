package com.zhaoyu.campus.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoyu.campus.common.utils.ServiceResult;
import com.zhaoyu.campus.consumer.remote.CollegeRemote;

@RestController
public class CollegeController {
	
	@Autowired
	CollegeRemote collegeRemote;
	
	@RequestMapping("/college/{id}")
    public ServiceResult index(@PathVariable("id") Integer id) {
        return collegeRemote.getById(id);
    }

}
