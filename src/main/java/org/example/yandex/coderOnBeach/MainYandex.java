package org.example.yandex.coderOnBeach;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MainYandex {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int testsCount = Integer.parseInt(reader.readLine());
        long[][] tests = new long[testsCount][];

        for (int i = 0; i < testsCount; i++) {
            int n = Integer.parseInt(reader.readLine());
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            long[] temp = new long[n];
            for (int j = 0; j < n; j++) {
                temp[j] = Integer.parseInt(tokenizer.nextToken());
            }
            tests[i] = temp;
        }

        for (long[] test : tests) {
            writer.write(findMinXor(test) + "\n");
        }
    }


    private static long findMinXor(long[] arr) {
        long minXor = Integer.MAX_VALUE;
        Arrays.sort(arr);
        for (int i = 1; i < arr.length; i++) {
            long xor = arr[i - 1] ^ arr[i];
            if (xor < minXor) {
                minXor = xor;
            }
        }
        return minXor;
    }
}
