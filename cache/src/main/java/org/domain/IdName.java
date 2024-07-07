package org.domain;

import java.io.*;

public class IdName implements Serializable {
    private static final long serialVersionUID = 1L;
    public String id;
    public String name;

    public static IdName Of(String id, String name) {
        return new IdName(id, name);
    }

    public IdName() {
    }

    public IdName(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
