package org.example.array;

import org.apache.commons.lang3.time.StopWatch;

import java.util.*;

public class Array {

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();

        for (int i = 0; i < 10; i++) {
            System.out.println("Попытка №" + i);
            int[] array = generateArray(new Random().nextInt(10));

            stopWatch.reset();
            stopWatch.start();
            System.out.println(Arrays.toString(twoSum(array, 6)));
            stopWatch.stop();
            System.out.println("Прошло времени, мс: " + stopWatch.getNanoTime());

            stopWatch.reset();
            stopWatch.start();
            Arrays.sort(array);
            System.out.println(Arrays.toString(twoSumSorted(array, 6)));
            stopWatch.stop();
            System.out.println("Прошло времени, мс: " + stopWatch.getNanoTime());

            stopWatch.reset();
            stopWatch.start();
            System.out.println(Arrays.toString(twoSum_secondVariant(array, 6)));
            stopWatch.stop();
            System.out.println("Прошло времени, мс: " + stopWatch.getNanoTime());
        }
    }

    public static int[] generateArray(int len) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= len; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        return numbers.stream().mapToInt(Integer::intValue).toArray();
    }

    public static int[] twoSum(int[] nums, int target) {
        int count = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                count++;
                if (nums[i] + nums[j] == target) {
                    System.out.println("Счетчик обычный = " + count);
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }

    public static int[] twoSumSorted(int[] nums, int target) {
        int count = 0;
        //Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                count++;
                if (nums[i] + nums[j] > target) continue;
                if (nums[i] + nums[j] == target) {
                    System.out.println("Счетчик отсортированный = " + count);
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }

    public static int[] twoSum_secondVariant(int[] nums, int target) {
        Map<Integer, Integer> numMap = new HashMap<>();
        int n = nums.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            numMap.put(nums[i], i);
        }

        for (int i = 0; i < n; i++) {
            int complement = target - nums[i];
            count++;
            if (numMap.containsKey(complement) && numMap.get(complement) != i) {
                System.out.println("Счетчик хешмапы = " + count);
                return new int[]{i, numMap.get(complement)};
            }
        }

        return new int[]{}; // No solution found
    }
}
