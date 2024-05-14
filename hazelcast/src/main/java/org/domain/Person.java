package org.domain;

import java.io.Serializable;

public class Person{
    public Integer id = 123;
    public String name = "John";
    public Integer age = 30;
    public String address = "Somewhere";
    public String phone = "123456789";

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
