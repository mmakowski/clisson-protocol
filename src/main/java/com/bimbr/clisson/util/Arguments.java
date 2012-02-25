package com.bimbr.clisson.util;

/**
 * Utility functions to verify arguments.
 *
 * @author mmakowski
 * @since 1.0.0 
 */
public final class Arguments {
    /**
     * Checks that argument is not null.
     * @param arg the argument whose value is to be checked
     * @param argName the name of the argument, for diagnostics
     * @return the value of {@code arg}
     * @throws IllegalArgumentException if {@code arg} is null
     * @author mmakowski
     * @since 1.0.0
     */
    public static <T> T nonNull(T arg, String argName) {
        if (arg == null) throw new IllegalArgumentException(argName + " is null");
        return arg;
    }

    /**
     * Checks that argument is a non-empty string.
     * @param arg the argument whose value is to be checked
     * @param argName the name of the argument, for diagnostics
     * @return the value of {@code arg}
     * @throws IllegalArgumentException if {@code arg} is an empty string
     * @author mmakowski
     * @since 1.0.0
     */
    public static String nonEmpty(String arg, String argName) {
        nonNull(arg, argName);
        if (arg.length() == 0) throw new IllegalArgumentException(argName + " is an empty string");
        return arg;
    }
    
    private Arguments() {}
}