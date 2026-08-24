package org.example.yandex.coderOnBeach;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Задача: "Минимальная непохожесть лежаков"
 * <p>
 * На пляже n свободных лежаков, каждому присвоено число a_i (0 <= a_i <= 1e9).
 * Непохожесть двух лежаков = побитовое XOR их чисел.
 * Нужно найти минимальное значение XOR среди всех пар лежаков.
 * <p>
 * Ограничения:
 * - 1 <= T <= 1000 (число тестов)
 * - 2 <= n <= 10^6 (сумма n по всем тестам <= 10^6)
 * - 0 <= a_i <= 10^9
 * <p>
 * Формат ввода:
 * - Первая строка: T
 * - Для каждого теста:
 * - строка: n
 * - строка: n чисел a_i
 * <p>
 * Формат вывода:
 * - Для каждого теста в отдельной строке вывести минимальный XOR.
 * <p>
 * Решение:
 * Отсортируем массив. Минимальный XOR среди всех пар достигается на соседних
 * элементах после сортировки. Доказательство: для любых i < j < k
 * (a_i ^ a_k) >= (a_i ^ a_j) и (a_i ^ a_k) >= (a_j ^ a_k) в смысле
 * минимальности, но строгое доказательство основано на том, что старший
 * бит, где различаются a_i и a_k, будет присутствовать и в XOR с промежуточным.
 * Поэтому достаточно проверить все соседние пары.
 * <p>
 * Сложность: O(n log n) на тест, суммарно O(N log N), где N <= 10^6.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));


        int tCount = Integer.parseInt(reader.readLine());

        List<Test> tests = new ArrayList<Test>(tCount);
        for (int i = 0; i < tCount; i++) {
            int n = Integer.parseInt(reader.readLine());
            Test test = new Test(n);
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < n; j++) {
                test.addSunbead(new Sunbead(Integer.parseInt(tokenizer.nextToken())));
            }
            test.sort();
            tests.add(test);
        }

        for (int i = 0; i < tests.size(); i++) {
            writer.write(String.valueOf(findMinDiff(tests.get(i))));
            writer.newLine();
        }
        reader.close();
        writer.close();

    }


    private static int findMinDiff(Test test) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < test.size() - 1; i++) {
            int localMin = test.get(i).compare(test.get(i + 1));
            if (localMin < min) {
                min = localMin;
            }
        }
        return min;
    }


    private static class Test {
        private final List<Sunbead> list;

        public Test(int count) {
            this.list = new ArrayList<>(count);
        }

        public void addSunbead(Sunbead sunbead) {
            list.add(sunbead);
        }

        public void sort() {
            Collections.sort(list);
        }

        public List<Sunbead> getList() {
            return list;
        }

        public int size() {
            return list.size();
        }

        public Sunbead get(int i) {
            return list.get(i);
        }
    }

    private static class Sunbead implements Comparable<Sunbead> {
        private final int value;

        public Sunbead(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public int compare(Sunbead other) {
            return value ^ other.getValue();
        }

        @Override
        public int compareTo(Sunbead o) {
            return Integer.compare(value, o.value);
        }
    }
}
