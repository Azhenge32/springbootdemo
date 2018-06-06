package com.azhen.controller;


import com.azhen.service.IsolationService;
import com.azhen.service.JdbcTemplateService;
import com.azhen.service.PropagationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/propagation")
public class PropagationController {
	@Resource
	PropagationService personService;
	@Resource
	IsolationService isolationService;
	@Resource
	JdbcTemplateService jdbcTemplateService;
	@RequestMapping("/never")
	public void never(){
		personService.never();
	}
	@RequestMapping("/require/fail")
	public void require(){
		personService.requireFail();
	}
	@RequestMapping("/requireNew/success")
	public void requireNew(){
		personService.requireNewSuccess();
	}

	@RequestMapping("/addPerson")
	public void jdbc() {
		jdbcTemplateService.addPerson();
	}

	@RequestMapping("/autocommit")
	public void jdbc1() {
		jdbcTemplateService.autocommit();
	}

	@RequestMapping("/readUncommit")
	public void readUncommit() {
		isolationService.readUncommit();
	}

	@RequestMapping("/readCommit")
	public void readCommit() {
		isolationService.readCommit();
	}

	@RequestMapping("/readRepeatable")
	public void readRepeatable() {
		isolationService.readRepeatable();
	}

	@RequestMapping("/readSeriable")
	public void readSeriable() {
		isolationService.readSeriable();
	}

	@RequestMapping("/parallelStreamSave")
	public String parallelStreamSave() {
		isolationService.parallelStreamSave();
		return "success";
	}
}
