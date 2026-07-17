package org.example.yandex.cupons;

import java.io.*;
import java.util.*;

public class Cupons {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out))) {

            int n = Integer.parseInt(reader.readLine());
            int[] prices = new int[n];
            int totalSum = 0;

            for (int i = 0; i < n; i++) {
                prices[i] = Integer.parseInt(reader.readLine());
                totalSum += prices[i];
            }

            boolean[] usedCoupon = new boolean[n];
            int unusedCoupons = 0;
            int usedCouponsCount = 0;

            for (int i = 0; i < n; i++) {
                if (prices[i] > 100 && !usedCoupon[i]) {
                    unusedCoupons++;
                    int maxIdx = findMaxIndexAfter(prices, i, usedCoupon);
                    if (maxIdx != -1) {
                        usedCouponsCount++;
                        usedCoupon[maxIdx] = true;
                        totalSum -= prices[maxIdx];
                        unusedCoupons--;
                    }
                }
            }

            // Вывод результатов
            writer.println(totalSum);
            writer.println(unusedCoupons + " " + usedCouponsCount);

            List<Integer> usedDays = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (usedCoupon[i]) {
                    usedDays.add(i + 1); // дни с 1
                }
            }
            Collections.sort(usedDays);
            for (int day : usedDays) {
                writer.println(day);
            }
        }
    }

    private static int findMaxIndexAfter(int[] prices, int from, boolean[] usedCoupon) {
        if (from + 1 >= prices.length) {
            return -1;
        }
        int maxValue = Integer.MIN_VALUE;
        int maxIndex = -1;
        for (int j = from + 1; j < prices.length; j++) {
            if (!usedCoupon[j] && prices[j] > maxValue) {
                maxValue = prices[j];
                maxIndex = j;
            }
        }
        return maxIndex;
    }
}
