package com.azhen.springdatajpa.dao;


import com.azhen.springdatajpa.domain.Person;
import com.azhen.springdatajpa.support.CustomRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonRepository extends CustomRepository<Person, Long> {
	List<Person> findByAddress(String address);
	
	Person findByNameAndAddress(String name, String address);
	
	@Query("select p from Person p where p.name= :name and p.address= :address")
	
	Person withNameAndAddressQuery(@Param("name") String name, @Param("address") String address);
	
	Person withNameAndAddressNamedQuery(String name, String address);

	// TODO save和saveAndFlush的区别

}
