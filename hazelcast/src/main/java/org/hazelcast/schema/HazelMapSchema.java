package org.hazelcast.schema;

public class HazelMapSchema {

    private String key;
    private Object data;
    public HazelMapSchema(Object data) {
        this.data = data;
    }

    public HazelMapSchema() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
