package com.bimbr.clisson.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Utility functions for working with collections.
 * 
 * @author mmakowski
 * @since 1.0.0
 */
public final class Collections {
    /**
     * @param source the map to copy
     * @return a copy of {@code source} that can't be modified
     * @since 1.0.0
     */
    public static <K, V> Map<K, V> immutableCopyOf(Map<K, V> source) {
        return java.util.Collections.unmodifiableMap(new HashMap<K, V>(source));
    }

    /**
     * @param source the list to copy
     * @return a copy of {@code source} that can't be modified
     * @since 1.0.0
     */
    public static <E> Set<E> immutableCopyOf(Set<E> source) {
        return java.util.Collections.unmodifiableSet(new TreeSet<E>(source));
    }
}
