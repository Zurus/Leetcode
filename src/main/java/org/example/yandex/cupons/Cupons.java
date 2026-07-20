package org.example.yandex.cupons;

import java.io.*;
import java.util.*;

public class Cupons {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out))) {

            int n = Integer.parseInt(reader.readLine());
            CouponsDynamic greedAlgo = new CouponsDynamic(n);

            int[] prices = new int[n];
            for (int i = 0; i < n; i++) {
                prices[i] = Integer.parseInt(reader.readLine());
            }

            greedAlgo.calc(prices);




            // Вывод результатов
//            writer.println(totalSum);
//            writer.println(unusedCoupons + " " + usedCouponsCount);
//
//            Collections.sort(usedDays);
//            for (int day : usedDays) {
//                writer.println(day);
//            }
        }
    }
}
