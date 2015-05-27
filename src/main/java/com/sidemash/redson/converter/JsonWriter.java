package com.sidemash.redson.converter;


import com.sidemash.redson.JsonValue;

public interface JsonWriter<T> {

    JsonValue toJsonValue(T object, JsonValue jsonValue);
}
