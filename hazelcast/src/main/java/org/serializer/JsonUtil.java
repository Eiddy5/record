package org.serializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.hazelcast.schema.HazelSchema;

import java.io.IOException;
import java.lang.reflect.Type;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    // 序列化
    public static <T> String serialize(HazelSchema<T> schema) throws IOException {
        return mapper.writeValueAsString(schema);
    }

    // 通用反序列化方法
    public static <T> HazelSchema<T> deserialize(String jsonString, Class<T> clazz) throws IOException {
        TypeFactory typeFactory = mapper.getTypeFactory();
        TypeReference<HazelSchema<T>> typeRef = new TypeReference<HazelSchema<T>>() {
            @Override
            public Type getType() {
                return typeFactory.constructParametricType(HazelSchema.class, clazz);
            }
        };
        return mapper.readValue(jsonString, typeRef);
    }
}
