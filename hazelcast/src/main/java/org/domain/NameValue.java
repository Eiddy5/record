package org.domain;

public class NameValue {
    public String name;
    public String value;


    public static NameValue Of(String name, String value) {
        return new NameValue(name, value);
    }

    public NameValue(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
