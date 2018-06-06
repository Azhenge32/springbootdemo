package com.azhen.springbootcache.service;


import com.azhen.springbootcache.domain.Person;

import java.util.List;

public interface DemoService {
	public Person save(Person person);
	
	public void remove(Long id);
	
	public Person findOne(Person person);

	List<Person> findAll();
}
