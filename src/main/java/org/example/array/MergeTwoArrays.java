package org.example.array;


/**
 * You are given two integer arrays nums1 and nums2, sorted in non-decreasing order,
 * and two integers m and n, representing the number of elements in nums1 and nums2 respectively.
 * <p>
 * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * Output: [1,2,2,3,5,6]
 */
public class MergeTwoArrays {


    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (n == 0) return;
        for (int i = m-1, j = n-1, k = nums1.length-1; k>=0; k--) {
            if (j<0 || (i>=0 && nums1[i]>nums2[j])) {
                nums1[k] = nums1[i--];
            } else {
                nums1[k] = nums2[j--];
            }
        }
    }

    /**
     * Реализация с двумя массивами
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void mergeThreePointers(int[] nums1, int m, int[] nums2, int n) {
        if (n == 0) return;
        int[] numsRes = new int[nums1.length];
        for (int i = 0, j = 0, k = 0; k < nums1.length; k++) {
            if (j >= n || (i < m && nums1[i] <= nums2[j])) {
                numsRes[k] = nums1[i++];
            } else {
                numsRes[k] = nums2[j++];
            }
        }

        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = numsRes[i];
        }
    }


    /**
     * Моя реализация
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void mergeMine(int[] nums1, int m, int[] nums2, int n) {
        if (n == 0) return;
        for (int i = 0, j = 0; i < nums1.length; i++) {
            if (nums1[i] == 0 && i >= m) {
                nums1[i] = nums2[j++];
            } else if (nums1[i] > nums2[j]) {
                int swap = nums1[i];
                nums1[i] = nums2[j];
                positioning(nums2, j, swap);
            }
        }
    }

    public void positioning(int[] nums, int start, int value) {
        for (int i = start + 1; i < nums.length; i++) {
            if (nums[i] >= value) {
                arrayShifting(nums, i - 1);
                nums[i - 1] = value;
                return;
            }
        }
        arrayShifting(nums, nums.length - 1);
        nums[nums.length - 1] = value;
    }

    public static void arrayShifting(int[] nums, int stopIdx) {
        for (int i = 0; i < stopIdx; i++) {
            nums[i] = nums[i + 1];
        }
    }
}
