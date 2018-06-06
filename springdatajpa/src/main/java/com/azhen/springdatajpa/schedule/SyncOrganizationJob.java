package com.azhen.springdatajpa.schedule;

import com.azhen.springdatajpa.dao.PersonRepository;
import com.azhen.springdatajpa.domain.Person;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Azhen
 * @date 2018/04/03
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class SyncOrganizationJob {
    @Resource
    private PersonRepository personRepository;

    // @Scheduled(fixedRate = 5000)
    public void test() {
        List<Person> all = personRepository.findAll();
        System.out.println("size: " + all.size());
        Person person = new Person();
        person.setAddress("111");
        person.setAge(1);
        person.setName("111");
        personRepository.save(person);
        System.out.println("save");
    }
}
