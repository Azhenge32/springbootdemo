package com.azhen.redis.demo;

import org.springframework.data.repository.CrudRepository;

import javax.annotation.Resource;

@Resource
public interface StudentRepository extends CrudRepository<Student, String> {
}
