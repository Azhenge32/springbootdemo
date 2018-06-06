package com.azhen.springbootcache.service;

import com.azhen.springbootcache.dao.PersonRepository;
import com.azhen.springbootcache.domain.Person;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Azhen
 * @date 2017/12/26
 */
@Service
@CacheConfig(cacheNames = "people")
public class DemoServiceImpl implements DemoService {
    @Resource
    PersonRepository personRepository;

    @CachePut(value = "people", key = "#person.id")
    public Person save(Person person) {
        Person p = personRepository.save(person);
        System.out.println("为id、可以为:" + p.getId() + "数据作了缓存");
        return p;
    }

    @CacheEvict(value = "people")
    public void remove(Long id) {
        System.out.println("删除了id、key为" + id + "的数据缓存");
        personRepository.deleteById(id);
    }

    @Cacheable(value = "people", key = "#person.id")
    public Person findOne(Person person) {
        Person p = personRepository.getOne(person.getId());
        System.out.println("为id、可以为:" + p.getId() + "数据作了缓存");
        return p;
    }

    // @Cacheable(value = "people")
    @Cacheable
    public List<Person> findAll() {
        List<Person> personList = personRepository.findAll();
        // System.out.println("为id、可以为:" + p.getId() + "数据作了缓存");
        System.out.println("对所有people做了缓存");
        return personList;
    }
}
