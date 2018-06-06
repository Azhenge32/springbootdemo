package com.azhen.springdatajpa.service;

import com.azhen.springdatajpa.dao.PersonRepository;
import com.azhen.springdatajpa.domain.Person;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PersonServiceImpl implements PersonService {
    @Resource
    private PersonRepository personRepository;

    @Override
    public void saveUser() {
        asyncSaveUser();
        System.out.println("main finish");
    }

    @Async
    public void asyncSaveUser() {
        Person person = new Person();
        person.setName("async");
        person.setAge(11);
        person.setAddress("async");
        personRepository.save(person);
        System.out.println("save success");
    }
}
