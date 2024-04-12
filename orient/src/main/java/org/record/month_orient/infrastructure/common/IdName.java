package org.record.month_orient.infrastructure.common;

public class IdName {
    private String id;
    private String name;

    public IdName(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public IdName() {
    }

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
}
