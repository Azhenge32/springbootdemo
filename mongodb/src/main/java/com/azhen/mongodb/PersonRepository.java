package com.azhen.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Azhen
 * @date 2018/03/31
 */
@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
    Person findByName(String name);
    @Query("{'age': ?0}")
    List<Person> withQueryFindByAge(Integer age);
}
