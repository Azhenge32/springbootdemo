package com.azhen.service;

import com.azhen.dao.PersonRepository;
import com.azhen.domain.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Azhen
 * @date 2018/04/15
 */
@Service
public class PropagationServiceImpl implements PropagationService{
    @Resource
    private PersonRepository personRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void requireFail() {
        Person person = new Person();
        person.setAddress("require");
        person.setAge(18);
        person.setName("require");
        personRepository.save(person);
        require1();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void require1() {
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void requireNewSuccess() {
       /* Person person = new Person();
        person.setAddress("requireNew");
        person.setAge(18);
        person.setName("requireNew");
        personRepository.save(person);
        require2();*/
       addPerson();
    }

    private void addPerson() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            Person person = new Person();
            person.setAddress(String.valueOf(i));
            person.setAge(18);
            person.setName(String.valueOf(i));
            people.add(person);
        }

        people.parallelStream().forEach(person -> {
            String threadName = Thread.currentThread().getName();
            person.setAddress(threadName);
            personRepository.save(person);
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void require2() {
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void supports() {
        Person person = new Person();
        person.setAddress("supports");
        person.setAge(18);
        person.setName("supports");
        personRepository.save(person);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void notSupported() {
        Person person = new Person();
        person.setAddress("notSupported");
        person.setAge(18);
        person.setName("notSupported");
        personRepository.save(person);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void never() {
        never1();
    }

    @Transactional(propagation = Propagation.NEVER)
    public void never1() {
        Person person = new Person();
        person.setAddress("never");
        person.setAge(18);
        person.setName("never");
        personRepository.save(person);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatory() {
        Person person = new Person();
        person.setAddress("mandatory");
        person.setAge(18);
        person.setName("mandatory");
        personRepository.save(person);
    }
}
