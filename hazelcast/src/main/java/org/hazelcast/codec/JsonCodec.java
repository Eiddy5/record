package org.hazelcast.codec;

import org.okr.server.hazelcast.client.codec.Codec;
import org.x7.json.JsonFactory;

public class JsonCodec implements Codec {

    @Override
    public String serialize(Object value) {
        return JsonFactory.ToJsonContent(value);
    }

    @Override
    public Object deserialize(String in, Class<?> clazz) {
        return JsonFactory.ParseClass(in, clazz);
    }
}
