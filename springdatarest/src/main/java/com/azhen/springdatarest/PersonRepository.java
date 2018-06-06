package com.azhen.springdatarest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author Azhen
 * @date 2018/03/31
 */
@RepositoryRestResource(path = "people")
public interface PersonRepository extends JpaRepository<Person, Long> {
    @RestResource(path = "nameStartsWith", rel = "nameStartsWith")
    Person findByNameStartsWith(String name);
}
