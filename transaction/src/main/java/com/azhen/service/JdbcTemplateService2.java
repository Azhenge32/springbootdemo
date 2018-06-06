package com.azhen.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class JdbcTemplateService2 {
    @Resource
    private JdbcTemplate jdbcTemplate;

    // @Transactional(value = "txManager1", propagation = Propagation.REQUIRES_NEW)
    public void addPerson() {
       addPerson1();
        addPerson2();
    }

    @Transactional(value = "txManager1", propagation = Propagation.REQUIRES_NEW)
    public void addPerson1() {
        String sql = "insert into ep2_person(name, age, address) VALUE ('11', 11, '11')";
        jdbcTemplate.update(sql);
    }

    @Transactional(value = "txManager1", propagation = Propagation.REQUIRES_NEW)
    public void addPerson2() {
        // String sql = "insert into ep2_person(name, age, address) VALUE ('22', 11, '22')";
        // jdbcTemplate.update(sql);
        throw new RuntimeException();
    }
}
