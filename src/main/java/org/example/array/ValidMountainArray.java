package org.example.array;

import java.util.Comparator;
import java.util.stream.IntStream;

public class ValidMountainArray {


    public static void main(String[] args) {
        int[] arr = {0, 3, 2, 1};
        System.out.println(validMountainArray(arr));
    }

    public static boolean validMountainArray(int[] arr) {
        int maxIdx = IntStream.range(0, arr.length)
                .boxed()
                .max(Comparator.comparing(i -> arr[i]))
                .get();

        boolean hasPassedMax = false;

        if (maxIdx == 0 || maxIdx == arr.length - 1) return false;

        for (int i = 1; i < arr.length - 1; i++) {
            hasPassedMax = i >= maxIdx;
            if (!hasPassedMax && arr[i] <= arr[i - 1]) {
                return false;
            }

            if (hasPassedMax && arr[i] <= arr[i + 1]) {
                return false;
            }
        }
        return hasPassedMax && true;
    }

}
