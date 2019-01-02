package com.zhaoyu.campus.autocode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {
	
	@GetMapping("/autoCode")
	public String autoCodePage(){
		return "page/autoCode";
	}
	
	@GetMapping("/vue")
	public String vue(){
		return "page/vue";
	}
	
}
