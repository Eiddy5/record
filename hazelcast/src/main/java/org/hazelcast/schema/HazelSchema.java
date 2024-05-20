package org.hazelcast.schema;

import java.io.Serializable;

public class HazelSchema<T> implements Serializable {

    private boolean isList;
    private T data;

    public HazelSchema() {
    }

    public HazelSchema(T data, boolean isList) {
        this.isList = isList;
        this.data = data;
    }

    public HazelSchema(T data) {
        this.data = data;
        this.isList = false;
    }

    public boolean isList() {
        return isList;
    }

    public void setList(boolean list) {
        isList = list;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
