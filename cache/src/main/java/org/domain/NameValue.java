package org.domain;

import java.io.Serial;
import java.io.Serializable;

public class NameValue implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public String name;
    public String value;

    public NameValue() {
    }

    public static NameValue Of(String name, String value) {
        return new NameValue(name, value);
    }

    public NameValue(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
