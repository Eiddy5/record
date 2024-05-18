package org.hazelcast.schema;

public enum MetadataEnum {
    Object(1),
    Collection(2);
    private Integer type;

    MetadataEnum(Integer type){
        this.type = type;
    }
    public Integer getType() {
        return type;
    }
}
