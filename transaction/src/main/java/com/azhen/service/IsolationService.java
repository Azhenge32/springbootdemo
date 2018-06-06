package com.azhen.service;

import com.azhen.dao.PersonRepository;
import com.azhen.domain.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class IsolationService {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private IsolationService isolationService;
    @Resource
    private PersonRepository personRepository;

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void readUncommit() {

    }

    public void readUncommit_serviceA() {

    }

    public void readUncommit_serviceB() {

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void readCommit() {
        readCommit_serviceA();
    }

    public void readCommit_serviceA() {
        String sql = "insert into ep2_person(name, age, address) VALUE ('readCommit', 11, 'readCommit')";
        jdbcTemplate.update(sql);
        sql = "update ep2_person set address = 'readCommit_serviceA' where name = 'readCommit'";
        jdbcTemplate.update(sql);
    }

    public void readCommit_serviceB() {

    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void readRepeatable() {

    }

    public void readRepeatable_serviceA() {

    }

    public void readRepeatable_serviceB() {

    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void readSeriable() {

    }

    public void readSeriable_serviceA() {

    }

    public void readSeriable_serviceB() {

    }


    public void parallelStreamSave() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 100; i ++) {
            Person person = new Person();
            person.setName("parallelStreamSave");
            person.setAge(i);
            personList.add(person);
        }

        // personRepository.saveAll(personList);
        personList.parallelStream().forEach(person -> {
            Thread thread = Thread.currentThread();
            System.out.println("thread name:" + thread.getName());
            personRepository.save(person);
        });
    }
}
