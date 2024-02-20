package com.engine.ext;

import java.util.HashMap;
import java.util.Map;

public class Person {
    private String id;
    private String name;
    private Map<String, Object> ext = new HashMap<>();

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

    public Map<String, Object> getExt() {
        return ext;
    }

    public void setExt(Map<String, Object> ext) {
        this.ext = ext;
    }

    @Override
    public String toString() {
        return "Person{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", ext=" + ext +
               '}';
    }
}
