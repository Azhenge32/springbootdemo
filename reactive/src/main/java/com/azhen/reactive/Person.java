package com.azhen.reactive;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ep2_person")
public class Person {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private Integer age;
	private String address;
	private String saveAll;
}
