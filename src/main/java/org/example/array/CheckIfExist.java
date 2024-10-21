package org.example.array;

import java.util.HashSet;
import java.util.Set;

public class CheckIfExist {

    public static void main(String[] args) {
        int arr[] = {-2, 0, 10, -19, 4, 6, -8};
        checkIfExist(arr);
    }

    public static boolean checkIfExistOld(int[] arr) {
        Boolean value = false;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i] == 2 * arr[j] && i != j) {
                    value = true;
                    break;
                } else value = false;
            }
            if (value == true) break;
        }
        return value;
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
