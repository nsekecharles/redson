package com.sidemash.redson.converter;

import com.sidemash.redson.Json;
import com.sidemash.redson.JsonObject;
import com.sidemash.redson.JsonValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum DefaultObjectConverter implements  JsonConverter<Object> {
    INSTANCE
    ;

    private static List<Method> getEligibleMethodsFor(Class<?> cl) {
        List<Method> result = new ArrayList<>();
        int modifier;
        for(Method method : cl.getDeclaredMethods()){
            modifier = method.getModifiers();
            if( Modifier.isPublic(modifier)
                    && (!method.isBridge())
                    && (!method.isSynthetic())
                    && (!Modifier.isStatic(modifier))
                    && (method.getName().startsWith("get")
                        || method.getName().startsWith("is")
                        || method.getName().startsWith("has")))
            result.add(method);

        }
        return result;
    }

    private static List<Field> getEligibleFieldsFor(Class<?> cl) {
        List<Field> result = new ArrayList<>();
        int modifier;
        for (Field field : cl.getDeclaredFields()) {
            if (!field.isSynthetic())
            {
                modifier = field.getModifiers();

                // If the field is not transient AND is not a reference to currentObject being analyzed
                if ( Modifier.isPublic(modifier)
                        && (!Modifier.isTransient(modifier))
                        && (!Modifier.isStatic(modifier)) )
                    result.add(field);
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> fromJsonValue(JsonValue jsonValue) {
        return jsonValue.toStringIndexedMapOf(Object.class);
    }

    @Override
    public JsonValue toJsonValue(Object obj, JsonValue jsonValue) {

        Class<?> tmpClass = obj.getClass();
        if(tmpClass.equals(Object.class))
            return JsonObject.EMPTY;


        Object fieldValue;
        Map<String, Object> attributeMap = new LinkedHashMap<>();
        JsonObject result = JsonObject.EMPTY;

        try {
            // Recursively build a list of all class in "obj" class hierarchy
            List<Class<?>> classList = Json.getClassHierarchyOf(tmpClass);
            System.out.println(classList);
            for (Class<?> c : classList) {
                attributeMap.clear();

                for(Field field : getEligibleFieldsFor(c)) {
                    fieldValue = field.get(obj);
                    if (fieldValue != obj )
                        attributeMap.put(field.getName(), fieldValue);
                }

                String methodNameWithoutPrefix;
                String methodNameInJson;
                for(Method method : getEligibleMethodsFor(c)) {
                    System.out.println(method.getName());
                    fieldValue = method.invoke(obj);
                    if (fieldValue != obj ){
                        methodNameWithoutPrefix = method.getName().replaceFirst("get|is|has", "");
                        methodNameInJson = String.format(
                                "%c%s",
                                Character.toLowerCase(methodNameWithoutPrefix.charAt(0)),
                                methodNameWithoutPrefix.substring(1)
                        );

                        attributeMap.put(methodNameInJson, fieldValue);
                    }
                    System.out.println("Finished");
                    System.out.println();
                }

                result = result.union(JsonObject.of(attributeMap));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}