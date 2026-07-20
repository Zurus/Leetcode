package org.example.yandex.cupons;

import org.example.utils.ArrayUtils;

import static org.example.utils.ArrayUtils.EMPTY_VAL;

public class CouponsDynamic {

    private static final int COUP_SUM = 100;
    private int n;
    private int[][] dp;

    public CouponsDynamic(int n) {
        this.n = n;
        int size = n + 1;
        dp = new int[size][size];
        ArrayUtils.fillEmptyArray(dp);
        dp[0][0] = 0;
    }


    public void calc(int[] prices) {
        for (int i = 0; i < n; i++) {
            int price = prices[i];

            for (int j = 0; j <= dp.length; j++) {
                if (dp[i][j] == EMPTY_VAL) {
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

        ArrayUtils.printArray(dp);
    }


    private int findBestJ() {
        int bestJ = 0;
        int min = Integer.MAX_VALUE;
        for (int j = 0; j <= n; j++) {
            if (dp[n][j] <= min) {
                bestJ++;
                min = dp[n][j];
            }
        }
        return bestJ;
    }

    private static int minWithMarker(int a, int b) {
        if (a == EMPTY_VAL) return b;
        if (b == EMPTY_VAL) return a;
        return Math.min(a, b);
    }
}
