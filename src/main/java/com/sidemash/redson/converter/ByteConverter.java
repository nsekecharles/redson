package com.sidemash.redson.converter;


import com.sidemash.redson.JsonNumber;
import com.sidemash.redson.JsonValue;

public enum ByteConverter implements  JsonConverter<Byte> {
    INSTANCE
    ;
    @Override
    public Byte fromJsonValue(JsonValue jsonValue) {
        return jsonValue.asByte();
    }

    @Override
    public JsonValue toJsonValue(Byte obj, JsonValue jsonValue) {
        return JsonNumber.of(obj);
    }
}
