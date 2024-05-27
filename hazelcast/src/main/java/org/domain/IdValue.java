package org.domain;

public class IdValue {
    public String id;
    public Object value;
    public int count = 0;

    public IdValue() {
    }

    public static IdValue Of(String id, Object value) {
        IdValue result = new IdValue();
        result.id = id;
        result.value = value;
        return result;
    }
}
