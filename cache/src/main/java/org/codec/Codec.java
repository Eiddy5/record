package org.codec;


import java.io.IOException;

public interface Codec<T> {
    String serialize(Object object) throws IOException;

    T deserialize(String in, Class<T> clazz) throws IOException, ClassNotFoundException;
}
