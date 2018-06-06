package com.azhen.springbootcache.dao;

import com.azhen.springbootcache.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Azhen
 * @date 2017/12/26
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> ,
        JpaSpecificationExecutor<Person> {
    List<Person> findByName(String name);
    /*List<Person> findByName(String name);

    @Query("select p from Person p where p.address like ?1")
    List<Person> findByNameLike(String name);

    List<Person> findByNameANDAddress(String name, String address);

    @Query("select p from Person p where p.name = :name order by name")
    List<Person> findFirst10ByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("update Person p set p.name = :name")
    int setName(@Param("name") String name);

    // @RestResource(path = "nameStartsWith", rel="nameStartsWith")
    Person findByNameStartingWith(@Param("name") String name);*/
}
