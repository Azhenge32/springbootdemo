package com.azhen.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class JdbcTemplateService {
    @Resource
    private JdbcTemplateService jdbcTemplateService;
    @Resource
    private JdbcTemplate jdbcTemplate;

    // @Transactional(value = "txManager1", propagation = Propagation.REQUIRED)
    @Transactional(propagation = Propagation.REQUIRED)
    public void addPerson() {
        jdbcTemplateService.addPerson1();
        // jdbcTemplateService.addPerson2();
        Thread myThread = new MyThread(jdbcTemplateService);
        myThread.start();
    }

    // @Transactional(value = "txManager1", propagation = Propagation.REQUIRED)
    @Transactional(propagation = Propagation.REQUIRED)
    public void addPerson1() {
        String sql = "insert into ep2_person(name, age, address) VALUE ('11', 11, '11')";
        jdbcTemplate.update(sql);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addPerson2() {
        String sql = "insert into ep2_person(name, age, address) VALUE ('22', 11, '22')";
        jdbcTemplate.update(sql);
        throw new RuntimeException();
    }

    // 放在一个新的线程运行的话，一定会创建一个新的事务
    private class MyThread extends Thread {
        private JdbcTemplateService service;
        MyThread(JdbcTemplateService service) {
            this.service = service;
        }
        @Override
        public void run() {
            service.addPerson2();
        }
    }

    public void autocommit() {
        //jdbcTemplateService.serviceB();
        //jdbcTemplateService.serviceA();
        String sql = "insert into ep2_person(name, age, address) VALUE ('BB', 11, 'BB')";
        jdbcTemplate.update(sql);
        throw new RuntimeException();
    }

    public void serviceB(){
        String sql = "insert into ep2_person(name, age, address) VALUE ('BB', 11, 'BB')";
        jdbcTemplate.update(sql);
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public void  serviceA(){
        String sql = "insert into ep2_person(name, age, address) VALUE ('AA', 11, 'AA')";
        jdbcTemplate.update(sql);
    }
}
