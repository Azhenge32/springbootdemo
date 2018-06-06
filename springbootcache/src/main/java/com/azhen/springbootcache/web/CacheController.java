package com.azhen.springbootcache.web;

import com.azhen.springbootcache.domain.Person;
import com.azhen.springbootcache.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CacheController {
	
	@Resource
	DemoService demoService;
	

	@RequestMapping("/put")
	public Person put(Person person){
		return demoService.save(person);
		
	}

	
	@RequestMapping("/able")
	public Person cacheable(Person person) {
		
		return demoService.findOne(person);
		
	}

	@RequestMapping("/all")
	public List<Person> all() {
		return demoService.findAll();

	}
	
	@RequestMapping("/evit")
	public String  evit(Long id){
		 demoService.remove(id);
		 return "ok";
		
	}
}
