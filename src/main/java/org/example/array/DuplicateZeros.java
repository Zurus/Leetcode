package org.example.array;


import java.util.Arrays;

/**
 * Given a fixed-length integer array arr, duplicate each occurrence of zero, shifting the remaining elements to the right.
 * <p>
 * Input: arr = [1,0,2,3,0,4,5,0]
 * Output: [1,0,0,2,3,0,0,4]
 * Explanation: After calling your function, the input array is modified to: [1,0,0,2,3,0,0,4]
 */
public class DuplicateZeros {

    public static void main(String[] args) {
        int[] arr = {1, 0, 2, 3, 0, 4, 5, 0};
        System.out.println(Arrays.toString(arr));
//        duplicateZeros1(arr);
        duplicateZeros2(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void duplicateZeros2(int[] arr) {
        int possibleDups = 0;
        int length_ = arr.length - 1;

        // Find the number of zeros to be duplicated
        // Stopping when left points beyond the last element in the original array
        // which would be part of the modified array
        for (int left = 0; left <= length_ - possibleDups; left++) {

            // Count the zeros
            if (arr[left] == 0) {

                // Edge case: This zero can't be duplicated. We have no more space,
                // as left is pointing to the last element which could be included
                if (left == length_ - possibleDups) {
                    // For this zero we just copy it without duplication.
                    arr[length_] = 0;
                    length_ -= 1;
                    break;
                }
                possibleDups++;
            }
        }

        // Start backwards from the last element which would be part of new array.
        int last = length_ - possibleDups;

        // Copy zero twice, and non zero once.
        for (int i = last; i >= 0; i--) {
            if (arr[i] == 0) {
                arr[i + possibleDups] = 0;
                possibleDups--;
                arr[i + possibleDups] = 0;
            } else {
                arr[i + possibleDups] = arr[i];
            }
        }
    }


    public static void duplicateZeros1(int[] arr) {
        int[] arrR = new int[arr.length];

        for (int i = 0, j = 0; i < arr.length; i++, j++) {
            if (j >= arr.length) break;
            if (arr[i] != 0) {
                arrR[j] = arr[i];
            } else {
                j++;
            }
        }
        System.arraycopy(arrR, 0, arr, 0, arr.length);
    }
}
