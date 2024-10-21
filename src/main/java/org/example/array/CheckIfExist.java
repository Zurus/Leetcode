package org.example.array;

import java.util.HashSet;
import java.util.Set;

/**
 * Given an array arr of integers, check if there exist two indices i and j such that :
 *
 * i != j
 * 0 <= i, j < arr.length
 * arr[i] == 2 * arr[j]
 */
public class CheckIfExist {

    public static void main(String[] args) {
        int arr[] = {-2, 0, 10, -19, 4, 6, -8};
        checkIfExist(arr);
    }

    public static boolean checkIfExist(int[] arr) {
        Set<Integer> set = new HashSet<>();
        for (int num : arr) {
            if ((set.contains(num * 2)) || (num % 2 == 0 && set.contains(num / 2))) {
                return true;
            } else {
                set.add(num);
            }
        }
        return false;
    }
}
