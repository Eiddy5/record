package org.hazelcast.metadata;


public class HazelMetadata {

    private MetadataType type;
    private Object value;

    public HazelMetadata(MetadataType type, Object value) {
        this.type = type;
        this.value = value;
    }


    public <T> T getValue() {
        return (T) value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public MetadataType getType() {
        return type;
    }

    public void setType(MetadataType type) {
        this.type = type;
    }

}
