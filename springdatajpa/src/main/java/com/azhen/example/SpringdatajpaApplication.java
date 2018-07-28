package com.azhen.example;

import com.azhen.example.support.CustomRepositoryFactoryBean;
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
