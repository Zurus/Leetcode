package org.example.utils;

import java.util.Arrays;

public class ArrayUtils {

    public ArrayUtils() {
        throw new UnsupportedOperationException("Util class");
    }


    public static void printArray(int[][] array) {
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    System.out.print(array[i][j] == -1 ? '*' : array[i][j]);
                    if (j < array.length - 1) System.out.print(' ');
                }
                System.out.println();
            }
    }
}
