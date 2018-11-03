package com.azhen.mbean;

//@Component
public class Hello {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name = "Hello World";

}
