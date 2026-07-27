package org.example.yandex.polyglot;

import java.io.*;
import java.util.*;

/**
 * 7. Полиглоты
 * Решена
 * Лёгкая
 * Каждый из
 * N
 * N школьников некоторой школы знает
 * M
 * i
 * M
 * i
 * ​
 * языков. Определите, какие языки знают все школьники и языки, которые знает хотя бы один из школьников.
 * <p>
 * Формат ввода
 * Первая строка входных данных содержит количество школьников
 * N
 * N. Далее идет
 * N
 * N чисел
 * M
 * i
 * M
 * i
 * ​
 * , после каждого из чисел идет
 * M
 * i
 * M
 * i
 * ​
 * строк, содержащих названия языков, которые знает
 * i
 * i-й школьник. Длина названий языков не превышает 1000 символов, количество различных языков не более 1000.
 * 1
 * ≤
 * N
 * ≤
 * 1000
 * 1≤N≤1000,
 * 1
 * ≤
 * M
 * i
 * ≤
 * 500
 * 1≤M
 * i
 * ​
 * ≤500.
 * <p>
 * Формат вывода
 * В первой строке выведите количество языков, которые знают все школьники. Начиная со второй строки - список таких языков. Затем - количество языков, которые знает хотя бы один школьник, на следующих строках - список таких языков.
 */
public class Polyglot {

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int studentsCount = Integer.parseInt(reader.readLine());
            Map<String, Integer> students = new TreeMap<>();

            for (int i = 0; i < studentsCount; i++) {
                int langCount = Integer.parseInt(reader.readLine());
                for (int j = 0; j < langCount; j++) {
                    String language = reader.readLine();
                    students.put(language, students.getOrDefault(language, 0) + 1);
                }
            }

            Set<String> common = new TreeSet<>(students.keySet());
            Set<String> atLeastOne = new TreeSet<>();

            students.forEach((s, num) -> {
                if (num == studentsCount) {
                    atLeastOne.add(s);
                }
            });

            writer.write(String.valueOf(atLeastOne.size()));
            writer.newLine();
            for (String lang : atLeastOne) {
                writer.write(lang);
                writer.newLine();
            }


            writer.write(String.valueOf(common.size()));
            writer.newLine();
            for (String lang : common) {
                writer.write(lang);
                writer.newLine();
            }
        }
    }


//    Мой вариант
//    public static void main(String[] args) throws IOException {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
//
//            int studentsCount = Integer.parseInt(reader.readLine());
//            Set<String> common = null;
//            Set<String> atLeastOne = new TreeSet<>();
//
//            for (int i = 0; i < studentsCount; i++) {
//                int langCount = Integer.parseInt(reader.readLine());
//                Set<String> current = new HashSet<>();
//
//                for (int j = 0; j < langCount; j++) {
//                    String language = reader.readLine();
//                    current.add(language);
//                    atLeastOne.add(language);
//                }
//
//                if (common == null) {
//                    common = new TreeSet<>(current);
//                } else {
//                    common.retainAll(current);
//                }
//            }
//
//            writer.write(String.valueOf(common.size()));
//            writer.newLine();
//            for (String lang : common) {
//                writer.write(lang);
//                writer.newLine();
//            }
//
//            writer.write(String.valueOf(atLeastOne.size()));
//            writer.newLine();
//            for (String lang : atLeastOne) {
//                writer.write(lang);
//                writer.newLine();
//            }
//
//        }
//    }
}
