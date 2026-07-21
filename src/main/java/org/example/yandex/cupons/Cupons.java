package org.example.yandex.cupons;

import org.example.utils.ArrayUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.example.utils.ArrayUtils.EMPTY_VAL;

public class Cupons {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out))) {

            int n = Integer.parseInt(reader.readLine());

            int[] prices = new int[n];
            for (int i = 0; i < n; i++) {
                prices[i] = Integer.parseInt(reader.readLine());
            }

            CouponsInnerDynamic cd = new CouponsInnerDynamic(n);
            cd.calc(prices);
            int minSum = cd.getMinSum();
            List<Integer> usedDays = cd.getUsedDays(prices);
            int K1 = cd.findBestJ();
            int K2 = usedDays.size();

            writer.println(minSum);
            writer.println(K1 + " " + K2);
            for (int day : usedDays) {
                writer.println(day);
            }
        }
    }


    public static class CouponsInnerDynamic {

        private static final int COUP_SUM = 100;
        private int n;
        private int[][] dp;

        public CouponsInnerDynamic(int n) {
            this.n = n;
            int size = n + 1;
            dp = new int[size][size];
            ArrayUtils.fillEmptyArray(dp);
            dp[0][0] = 0;
        }

        public void calc(int[] prices) {
            for (int i = 0; i < n; i++) {
                int price = prices[i];

                for (int j = 0; j < n; j++) {
                    if (dp[i][j] == -1) {
                        continue;
                    }

                    int newJ = j + (price > 100 ? 1 : 0);
                    if (newJ <= n) {
                        dp[i + 1][newJ] = minWithMarker(dp[i + 1][newJ], dp[i][j] + price);
                    }

                    if (j > 0) {
                        dp[i + 1][j - 1] = minWithMarker(dp[i + 1][j - 1], dp[i][j]);
                    }
                }
            }
        }

        public int getMinSum() {
            int j = findBestJ();
            return dp[n][j];
        }

        public List<Integer> getUsedDays(int[] prices) {
            int j = findBestJ();
            List<Integer> usedDays = new ArrayList<>();
            int i = n;
            while (i > 0) {
                int price = prices[i - 1];
                if (j + 1 <= n && dp[i - 1][j + 1] != -1 && dp[i - 1][j + 1] == dp[i][j]) {
                    usedDays.add(i);
                    j = j + 1;
                } else {
                    if (price > 100) {
                        j = j - 1;
                    }
                }
                i--;
            }
            Collections.sort(usedDays);
            return usedDays;
        }

        public int findBestJ() {
            int bestJ = 0;
            int min = Integer.MAX_VALUE;
            for (int j = 0; j <= n; j++) {
                if (dp[n][j] <= min && dp[n][j] != -1) {
                    bestJ = j;
                    min = dp[n][j];
                }
            }
            return bestJ;
        }

        private static int minWithMarker(int a, int b) {
            if (a == -1) return b;
            if (b == -1) return a;
            return Math.min(a, b);
        }
    }
}
