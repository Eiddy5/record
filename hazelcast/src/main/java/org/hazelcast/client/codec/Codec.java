package org.hazelcast.client.codec;

public interface Codec {

    String serialize(Object value);

   Object deserialize(String json, Class<?> clazz);
}
