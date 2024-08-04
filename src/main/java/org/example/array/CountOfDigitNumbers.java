package org.example.array;

public class CountOfDigitNumbers {
    private static final int[] nums =
            {123123, 12, 123, 4, 123, 1234};

    public static void main(String[] args) {
        for (int num : nums) {
            System.out.printf("Количество цифр в числе %d = %d %n", num, (int) Math.log10(num) + 1);
        }
    }
}
