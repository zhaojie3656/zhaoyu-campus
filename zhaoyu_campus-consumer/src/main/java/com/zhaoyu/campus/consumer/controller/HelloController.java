package com.zhaoyu.campus.consumer.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoyu.campus.consumer.remote.HelloRemote;


@RestController
public class HelloController {
	
	@Autowired
    HelloRemote helloRemote;
	
    @RequestMapping("/hello/{name}")
    public String index(@PathVariable("name") String name) {
        return helloRemote.hello(name);
    }
    
    @RequestMapping("/test/{name}")
    public String test(@PathVariable("name") String name) {
    	Map<String, String> map = new HashMap<>();
    	map.put("college_name", name);
    	map.put("college_id", "18");
    	System.out.println(map);
        return helloRemote.test(map);
    }

}
