package com.zhaoyu.campus.consumer.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhaoyu.campus.common.utils.ServiceResult;

@FeignClient(name= "campus-producer")
public interface CollegeRemote {
	
	@RequestMapping(value = "/college/getById")
    public ServiceResult getById(@RequestParam(value = "id") Integer id);

}
