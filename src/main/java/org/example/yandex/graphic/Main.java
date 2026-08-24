package org.example.yandex.graphic;

import java.io.*;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

import static org.example.utils.ArrayUtils.parse;

/*
Описание задачи:
Пете на работе выдали n задач.
Каждый день, начиная с первого, Петя может выполнить ровно одну задачу.
Про каждую задачу известен последний день d_i, когда её можно выполнить,
и величина стресса w_i, который Петя испытает, если задача не будет выполнена
в срок и надо будет просить помощи коллег.

Помогите Пете решить, в каком порядке выполнять задачи, чтобы уменьшить его
суммарный стресс.

Формат ввода:
В первой строке дано единственное натуральное число n (1 ≤ n ≤ 200 000) —
количество задач.
Затем следует n строк, в каждой из которых содержится по два числа d_i и w_i
(1 ≤ d_i ≤ 200 000, 1 ≤ w_i ≤ 200 000) — последний день, когда можно выполнить
задачу, и стресс при пропуске дедлайна для i-й задачи.

Формат вывода:
Выведите единственное число, равное минимальному возможному суммарному стрессу.
*/
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(reader.readLine());
        Task[] tasks = new Task[n];
        for (int i = 0; i < n; i++) {
            String[] line = reader.readLine().split(" ");
            int d = Integer.parseInt(line[0]);
            int w = Integer.parseInt(line[1]);
            tasks[i] = new Task(d, w);
        }

        Arrays.sort(tasks);
        long sum = 0;
//        PriorityQueue в Java — это реализация очереди с приоритетом, основанная на бинарной куче.
//        По умолчанию она работает как min‑heap (наименьший элемент находится на вершине и извлекается первым).
        Queue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            pq.add(tasks[i].getWeight());
            if (pq.size() > tasks[i].getdLine()) {
                sum += pq.poll();
            }
        }

        writer.write(String.valueOf(sum));
        reader.close();
        writer.close();
    }


    public static class Task implements Comparable<Task> {
        private int dLine;
        private int weight;

        public Task(int dLine, int weight) {
            this.dLine = dLine;
            this.weight = weight;
        }

        public int getdLine() {
            return dLine;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public int compareTo(Task o) {
            return Integer.compare(dLine, o.dLine);
        }

        @Override
        public String toString() {
            return "Task{" +
                    "dLine=" + dLine +
                    ", weight=" + weight +
                    '}';
        }
    }
}
