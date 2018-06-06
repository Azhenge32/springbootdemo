package com.azhen.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author Azhen
 * @date 2018/03/31
 */
@Document
public class Person {
    private String id;
    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    private Collection<Location> locations = new LinkedHashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Collection<Location> getLocations() {
        return locations;
    }

    public void setLocations(Collection<Location> locations) {
        this.locations = locations;
    }
}
