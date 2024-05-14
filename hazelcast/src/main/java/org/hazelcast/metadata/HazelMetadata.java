package org.hazelcast.metadata;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.domain.Person;
import org.hazelcast.client.protobuf.Decoder;
import org.hazelcast.client.protobuf.Encoder;
import org.hazelcast.client.protobuf.JacksonDecoder;
import org.hazelcast.client.protobuf.JacksonEncoder;

import java.io.IOException;
import java.util.List;

public class HazelMetadata {

    private MetadataEnum type;
    @JsonSerialize(using = ValueSerializer.class)
    @JsonDeserialize(using = ValueDeserializer.class)
    private Object value;

    public HazelMetadata() {
    }
    public static class ValueSerializer extends StdSerializer<Object> {

        public ValueSerializer() {
            this(null);
        }

        public ValueSerializer(Class<Object> t) {
            super(t);
        }

        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            ObjectMapper mapper = (ObjectMapper) gen.getCodec();
            if (value instanceof List) {
                mapper.writeValue(gen, value);
            } else {
                mapper.writeValue(gen, value);
            }
        }
    }

    public static class ValueDeserializer extends JsonDeserializer<Object> {
        private static final ObjectMapper mapper = new ObjectMapper();

        @Override
        public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node = p.getCodec().readTree(p);
            MetadataEnum type = MetadataEnum.valueOf(node.get("type").asText());
            JsonNode valueNode = node.get("value");

            if (type == MetadataEnum.Collection) {
                return mapper.convertValue(valueNode, new TypeReference<List<Person>>() {});
            } else {
                return mapper.convertValue(valueNode, Person.class);
            }
        }
    }

    public HazelMetadata(MetadataEnum type, Object value) {
        this.type = type;
        this.value = value;
    }


    public <T> T getValue() {
        return (T) value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public MetadataEnum getType() {
        return type;
    }

    public void setType(MetadataEnum type) {
        this.type = type;
    }

}
