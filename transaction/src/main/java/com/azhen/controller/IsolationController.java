package com.azhen.controller;


import com.azhen.service.IsolationService;
import com.azhen.service.JdbcTemplateService;
import com.azhen.service.PropagationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/isolation")
public class IsolationController {
	@Resource
	IsolationService isolationService;

	@RequestMapping("/likeCount")
	public void likeCount() {
		isolationService.likeCount();
	}
}
