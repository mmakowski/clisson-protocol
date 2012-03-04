package com.bimbr.clisson.protocol;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Provides mapping between strings representing types of data and corresponding classes.
 * 
 * @author mmakowski
 * @since 1.0.0
 */
public final class Types {
    private static final Map<String, Class<?>> classes = classMap();
    
    /**
     * @param cls class to produce id for
     * @return string identifier for {@code cls}
     */
    public static String id(Class<?> cls) {
        return cls.getSimpleName().toLowerCase();
    }
    
    /**
     * @param type type name
     * @return class corresponding to {@code type}
     * @throws IllegalArgumentException if {@code type} is not a Clisson protocol data type
     */
    public static Class<?> classFor(String type) {
        if (!classes.containsKey(type)) throw new IllegalArgumentException("unknown type: " + type);
        return classes.get(type);
    }
    
    private static Map<String, Class<?>> classMap() {
        Map<String, Class<?>> classes = new TreeMap<String, Class<?>>();
        classes.put(id(Checkpoint.class), Checkpoint.class);
        classes.put(id(Error.class),      Error.class);
        classes.put(id(Trail.class),      Trail.class);
        return Collections.unmodifiableMap(classes);
    }

    private Types() {}
}
