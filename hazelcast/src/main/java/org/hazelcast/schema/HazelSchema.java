package org.hazelcast.schema;

import java.io.Serializable;

public class HazelSchema<T> implements Serializable {

    private String key;
    private T data;
    public HazelSchema(T data) {
        this.data = data;
    }

    public HazelSchema() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
