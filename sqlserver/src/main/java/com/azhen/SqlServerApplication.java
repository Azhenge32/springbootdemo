package com.azhen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SqlServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqlServerApplication.class, args);
	}
}
