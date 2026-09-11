package org.example.yandex.min.line;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {

    /*
     * Рассмотрим последовательность целых чисел длины n.
     *
     * По ней двигается «окно» длины k: сначала в «окне» находятся первые k чисел,
     * на следующем шаге в «окне» уже будут находиться k чисел, начиная со второго,
     * и так далее до конца последовательности.
     *
     * Требуется для каждого положения «окна» определить минимум в нём.
     *
     * Формат ввода:
     * В первой строке входных данных содержатся два, разделённых пробелом,
     * натуральных числа n и k (n ≤ 150000, k ≤ 10000, k ≤ n) — длины
     * последовательности и «окна», соответственно.
     *
     * На следующей строке через пробел записаны n целых чисел — сама
     * последовательность.
     *
     * Формат вывода:
     * Выведите n − k + 1 строк. В каждой строке должно быть одно число —
     * минимум для соответствующего положения «окна».
     *
     * Примечание:
     * Для первого тестового примера ответ выглядит следующим образом:
     *
     * 7 3
     * 1 3 2 4 5 3 1
     *
     * Рассмотрим все доступные положения скользящего окна:
     *
     * |1 3 2| 4 5 3 1 - min(1, 3, 2) = 1
     * 1 |3 2 4| 5 3 1 - min(3, 2, 4) = 2
     * 1 3 |2 4 5| 3 1 - min(2, 4, 5) = 2
     * 1 3 2 |4 5 3| 1 - min(4, 5, 3) = 3
     * 1 3 2 4 |5 3 1| - min(5, 3, 1) = 1
     */
    public static void main(String[] args) {
        //7 3
        //1 3 2 4 5 3 1

        //4 2
        //4 1 3 2
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            String[] data = reader.readLine().split(" ");
            int n = Integer.parseInt(data[0]);
            int k = Integer.parseInt(data[1]);
//            int[] nums = Arrays.stream(reader.readLine()
//                            .split(" "))
//                    //.filter(data->String.)
//                    .mapToInt(Integer::parseInt)
//                    .toArray();

            int[] nums = new int[n];
            int idx = 0;
            StringTokenizer st = new StringTokenizer(reader.readLine());
            while (st.hasMoreTokens() && idx < n) {
                nums[idx++] = Integer.parseInt(st.nextToken());
            }

            int cycleCount = n - k + 1;
            //solveTask(nums, k, cycleCount);
            Deque<Integer> deque = new ArrayDeque<>();

            /*
            Что делать на каждом шаге
Для каждого числа a[i] по порядку:

Шаг 1. Чистим конец.
Смотри на последний элемент списка. Пока его значение ≥ a[i] — вычёркивай его. Остановись, когда список пуст или последнее значение < a[i].

Шаг 2. Добавляем.
Запиши i:a[i] в конец списка.

Шаг 3. Чистим начало.
Смотри на первый элемент списка. Если его позиция < i - k + 1 — вычёркивай его.

Шаг 4. Вывод (только если i ≥ k - 1).
Первое значение в списке = минимум окна. Запиши его в ответ.

Шаг 5. Переходи к следующему i.
             */


            for (int i = 0; i < n; i++) {
                // Чистим хвост: пока последний элемент очереди >= nums[i],
                // он бесполезен — nums[i] меньше и стоит правее.
                popBack(deque, nums, nums[i]);

                // Кладём текущий индекс в хвост.
                deque.addLast(i);

                // Убираем голову, если она вышла за левую границу окна.
                popFront(deque, i, k);

                // Окно сформировалось — минимум лежит в голове.
                if (i >= k - 1) {
                    writer.write(Integer.toString(nums[deque.peekFirst()]));
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Убирает с хвоста очереди индексы, значения по которым >= value.
     */
    public static void popBack(Deque<Integer> deque, int[] nums, int value) {
        while (!deque.isEmpty() && nums[deque.peekLast()] >= value) {
            deque.pollLast();
        }
    }

    /**
     * Убирает голову очереди, если её индекс вышел за пределы окна [i - k + 1, i].
     */
    public static void popFront(Deque<Integer> deque, int i, int k) {
        if (!deque.isEmpty() && deque.peekFirst() <= i - k) {
            deque.pollFirst();
        }
    }

    public static int solveDeque(int[] array, int startIdx, int len) {
        //int[] deque = new int[n];
        int head = 0;
        int tail = 0;


        return -1;
    }


    //Мое решение, неэффективно по времени
    public static void solveTask(int[] array, int len, int cycleCount) {
        int minIdx = -1;
        for (int i = 0; i < cycleCount; i++) {
            if (minIdx != -1 && isInclude(minIdx, i, len)) {
                minIdx = array[minIdx] < array[i + len - 1] ? minIdx : i + len - 1;
            } else {
                minIdx = findMinIdx(array, i, len);
            }
            System.out.println(array[minIdx]);
        }
    }

    public static boolean isInclude(int idx, int startIdx, int len) {
        return idx >= startIdx && idx <= startIdx + len - 1;
    }

    public static int findMinIdx(int[] array, int startIdx, int len) {
        int minIdx = startIdx;
        for (int i = startIdx; i < startIdx + len; i++) {
            if (array[minIdx] > array[i]) {
                minIdx = i;
            }
        }
        return minIdx;
    }
}
