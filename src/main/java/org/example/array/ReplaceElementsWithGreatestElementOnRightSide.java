package org.example.array;


import java.util.Arrays;

/**
 * Given an array arr, replace every element in that array with the greatest element among the elements to its right, and replace the last element with -1.
 * <p>
 * After doing so, return the array.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: arr = [17,18,5,4,6,1]
 * Output: [18,6,6,6,1,-1]
 * Explanation:
 * - index 0 --> the greatest element to the right of index 0 is index 1 (18).
 * - index 1 --> the greatest element to the right of index 1 is index 4 (6).
 * - index 2 --> the greatest element to the right of index 2 is index 4 (6).
 * - index 3 --> the greatest element to the right of index 3 is index 4 (6).
 * - index 4 --> the greatest element to the right of index 4 is index 5 (1).
 * - index 5 --> there are no elements to the right of index 5, so we put -1.
 */
public class ReplaceElementsWithGreatestElementOnRightSide {


    public static void main(String[] args) {
        int[] nums = {17, 18, 5, 4, 6, 1};
//        int[] nums = {57010, 40840, 69871, 14425, 70605};
        try {
            replaceElementsLT(nums);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Arrays.stream(nums).forEach(System.out::println);
    }


    public static int[] replaceElementsLT(int[] arr) {
        int i = 0;
        int max = 0;
        int maxIdx = i;

        while (i < arr.length - 1) {
            if (i == maxIdx && maxIdx < arr.length - 1) {
                max = Integer.MIN_VALUE;
                for (int k = i + 1; k < arr.length; k++) {
                    if (max < arr[k]) {
                        max = arr[k];
                        maxIdx = k;
                    }
                }
            }
            while (i < maxIdx) {
                arr[i++] = max;
            }
        }

        arr[arr.length - 1] = -1;
        return arr;
    }


    public static int[] replaceElements(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {

            int maxIdx = i + 1;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] > arr[maxIdx]) {
                    maxIdx = j;
                }
            }
            arr[i] = arr[maxIdx];
        }

        arr[arr.length - 1] = -1;
        return arr;
    }

}
