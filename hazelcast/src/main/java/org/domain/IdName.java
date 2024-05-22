package org.domain;

public class IdName {
    public String id;
    public String name;

    public static IdName Of(String id, String name) {
        return new IdName(id, name);
    }

    public IdName(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
