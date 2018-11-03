package com.azhen.example.web;


import com.azhen.example.dao.PersonRepository;
import com.azhen.example.domain.Person;
import com.azhen.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;


@RestController
public class TimeZoneController {
	@RequestMapping("/timeZone")
	public String auto(Person person){
		return Calendar.getInstance().getTimeZone().getDisplayName();
	}
}
