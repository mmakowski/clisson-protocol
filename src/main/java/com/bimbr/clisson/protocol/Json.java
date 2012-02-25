package com.bimbr.clisson.protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Utility functions for serialising to/deserialising from JSON.
 * 
 * @author mmakowski
 * @since 1.0.0
 */
public final class Json {
    /**
     * Converts supplied object to JSON.
     * @param o object to convert to JSON
     * @return JSON representation of {@code o}
     */
    public static String jsonFor(Object o) {
        return gson().toJson(o);
    }

    /**
     * Creates an object based on supplied JSON.
     * @param json the JSON to turn to an object
     * @param cls the class of the object to return
     * @return object representing {@code json}
     */
    public static <T> T fromJson(String json, Class<T> cls) {
        return gson().fromJson(json, cls);
    }
    
    private static Gson gson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ").create();
    }
}
