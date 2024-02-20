package com.engine.jsonObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonObject extends JSONObject {

    public static <T> T toJavaObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

}
