package org.example.utils;

public class StringUtils {
    private static String BINARY_INFO = "Битовый вид %s исходный вид %d";

    private StringUtils() {
    }

    public static String toBinaryString(int i) {
        return String.format(BINARY_INFO, Integer.toBinaryString(i), i);
    }
}
