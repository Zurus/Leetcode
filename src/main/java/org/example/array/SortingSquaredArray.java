package org.example.array;

import java.util.Arrays;

public class SortingSquaredArray {

    public static void main(String[] args) {
//        int[] array = {17, 14, 15, 28, 6, 8, -6, 1, 3, 18};
        int[] array = {6, 5, 1, 3, 8, 4, 7, 9, 2};
        System.out.println("Unsorted Array: " + Arrays.toString(array));
        quickSort(array, 0, array.length - 1);
        System.out.println("  Sorted Array: " + Arrays.toString(array));
    }

    public static void quickSort(int[] sortArr, int low, int high) {
        if (sortArr.length == 0 || low >= high) {
            return;
        }
        int middle = low + (high - low) / 2;
        int border = sortArr[middle];
        int i = low, j = high;

        while (i <= j) {
            while (sortArr[i] < border) {
                i++;
            }
            while (sortArr[j] > border) {
                j--;
            }
            if (i <= j) {
                int swap = sortArr[i];
                sortArr[i] = sortArr[j];
                sortArr[j] = swap;
                i++;
                j++;
            }
        }
        if (low < j) {
            quickSort(sortArr, low, j);
        }
        if (high > i) {
            quickSort(sortArr, i, high);
        }
    }


//    public static void quickSort(int[] arr, int low, int high) {
//        if (low < high) {
//            int pi = partition(arr, low, high);
//
//
//            quickSort(arr, low, pi - 1);
//            quickSort(arr, pi + 1, high);
//        }
//    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;

                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;


        return i + 1;
    }
}

