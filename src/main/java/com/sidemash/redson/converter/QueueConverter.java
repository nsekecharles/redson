package com.sidemash.redson.converter;

import com.sidemash.redson.JsonArray;
import com.sidemash.redson.JsonValue;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.Queue;

public class QueueConverter<T> implements JsonContainerConverter<Queue<T>> {

    @Override
    public Queue<T> fromJsonValue(JsonValue jsonValue,Type type) {
        Queue<T> result = new LinkedList<>();
        JsonArray array = (JsonArray) jsonValue;
        ParameterizedType p = (ParameterizedType) type;
        for(JsonValue value : array)
            result.add(value.asType(p.getActualTypeArguments()[0]));

        return result;
    }

        @Override
    public JsonValue toJsonValue(Queue<T> obj, JsonValue jsonValue) {
        return JsonArray.of(obj);
    }
}
