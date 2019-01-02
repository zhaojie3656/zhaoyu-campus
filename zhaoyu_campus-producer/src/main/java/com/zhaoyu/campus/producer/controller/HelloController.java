package com.zhaoyu.campus.producer.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoyu.campus.common.utils.JsonUtils;
import com.zhaoyu.campus.common.utils.ServiceResult;
import com.zhaoyu.campus.producer.entity.TCollegeEntity;

@RestController
public class HelloController {
	@RequestMapping("/hello")
    public String index(@RequestParam String name) {
        return "hello "+name+"，this is first messge";
    }
	
	@RequestMapping("/test")
    public ServiceResult test(@RequestBody Map<String, String> entity) {
		String aa = JsonUtils.objectToJson(entity);
		System.out.println(aa);
        return ServiceResult.ok(aa);
    }
}
