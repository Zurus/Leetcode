package org.example.utils;

import java.util.Arrays;

public class ArrayUtils {

    public static final int EMPTY_VAL = -1;

    public ArrayUtils() {
        throw new UnsupportedOperationException("Util class");
    }

    public static void printArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                // заменяем -1 на "-" и выводим с шириной 4 символа, выравнивание вправо
                String value = (array[i][j] == -1) ? "-" : Integer.toString(array[i][j]);
                System.out.printf("%4s", value);   // можно изменить 4 на нужную ширину
                if (j < array[i].length - 1) {     // исправлено!
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }

    public static void fillEmptyArray(int[][] array) {
        for (int[] row : array) {
            Arrays.fill(row, -1);
        }
    }
}
