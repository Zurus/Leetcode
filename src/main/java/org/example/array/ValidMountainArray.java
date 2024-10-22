package org.example.array;

import java.util.Comparator;
import java.util.stream.IntStream;

public class ValidMountainArray {


    public static void main(String[] args) {
        int[] arr = {0, 3, 2, 1};
        System.out.println(validMountainArray(arr));
    }

    public boolean validMountainArrayLT(int[] A) {
        int N = A.length;
        int i = 0;

        // walk up
        while (i + 1 < N && A[i] < A[i + 1])
            i++;

        // peak can't be first or last
        if (i == 0 || i == N - 1)
            return false;

        // walk down
        while (i + 1 < N && A[i] > A[i + 1])
            i++;

        return i == N - 1;
    }


    public static boolean validMountainArray(int[] arr) {
        int st = 0;
        int len = arr.length;
        int i = st;

        while (i < arr.length - 1) {
            if (arr[i] < arr[i + 1]) {
                i++;
            } else {
                break;
            }
        }

        if (i == 0 || i == len - 1) {
            return false;
        }
        while (i < arr.length - 1) {
            if (arr[i] > arr[i + 1]) {
                i++;
            } else {
                break;
            }
        }
        return i == len;
    }

    public static boolean validMountainArrayOld(int[] arr) {
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
