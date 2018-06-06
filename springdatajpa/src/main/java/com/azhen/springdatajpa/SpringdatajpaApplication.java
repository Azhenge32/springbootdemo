package com.azhen.springdatajpa;

import com.azhen.springdatajpa.dao.PersonRepository;
import com.azhen.springdatajpa.support.CustomRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
@EnableScheduling
@EnableAsync
public class SpringdatajpaApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringdatajpaApplication.class, args);
	}
}
