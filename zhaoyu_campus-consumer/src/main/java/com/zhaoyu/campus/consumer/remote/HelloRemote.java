package com.zhaoyu.campus.consumer.remote;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= "campus-producer")
public interface HelloRemote {
	
	@RequestMapping(value = "/hello")
    public String hello(@RequestParam(value = "name") String name);

	@RequestMapping(value = "/test")
	public String test(@RequestBody Map<String, String> map);

}
