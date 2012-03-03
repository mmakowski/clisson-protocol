package com.bimbr.clisson.protocol;

import static com.bimbr.clisson.util.Arguments.nonEmpty;
import static com.bimbr.clisson.util.Arguments.nonNull;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Utility functions for serialising to/deserialising from JSON.
 * 
 * @author mmakowski
 * @since 1.0.0
 */
public final class Json {
    /**
     * Converts supplied object to JSON.
     * @param object object to convert to JSON
     * @return JSON representation of {@code o}
     */
    public static String jsonFor(Object object) {
        return gson().toJson(nonNull(object, "object"));
    }

    /**
     * Creates an object based on supplied JSON.
     * @param json the JSON to turn to an object
     * @param cls the class of the object to return
     * @return object representing {@code json}
     */
    public static <T> T fromJson(String json, Class<T> cls) {
        return gson().fromJson(nonEmpty(json, "json"), cls);
    }
    
    private static Gson gson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ")
                                .registerTypeAdapter(Event.class, new InterfaceAdapter<Event>())
                                .create();
    }
    
    // InterfaceAdapter wraps objects which are stored with only their interface known statically with concrete type information
    // so that deserialisation is possible.
    private static class InterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
        /**
         * @see JsonSerializer#serialize(Object, Type, JsonSerializationContext)
         */
        public JsonElement serialize(T object, Type interfaceType, JsonSerializationContext context) {
            final JsonObject wrapper = new JsonObject();
            wrapper.addProperty("type", Types.id(object.getClass()));
            wrapper.add("data", context.serialize(object));
            return wrapper;
        }

        /**
         * @see JsonDeserializer#deserialize(JsonElement, Type, JsonDeserializationContext)
         */
        public T deserialize(JsonElement elem, Type interfaceType, JsonDeserializationContext context) throws JsonParseException {
            final JsonObject wrapper = (JsonObject) elem;
            final JsonElement typeElem = get(wrapper, "type");
            final JsonElement data = get(wrapper, "data");
            final Type actualType = Types.classFor(typeElem.getAsString()); 
            return context.deserialize(data, actualType);
        }

        private JsonElement get(final JsonObject wrapper, String memberName) {
            final JsonElement elem = wrapper.get(memberName);
            if (elem == null) throw new JsonParseException("no '" + memberName + "' member found in what was expected to be an interface wrapper");
            return elem;
        }

    }
    
}
