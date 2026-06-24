package org.example.yandex;


import java.io.*;
import java.util.Arrays;

/**
 * Класс для построения игрового поля "Сапёр" по заданным размерам и координатам мин.
 * <p>
 * Поле представляет собой матрицу, где каждая ячейка содержит либо мину ('*'),
 * либо число от 0 до 8, показывающее количество мин в соседних клетках.
 * Соседними считаются восемь клеток, имеющих общую сторону или угол.
 * </p>
 * <p>
 * Координаты мин задаются в формате (строка, столбец), нумерация с 1.
 * </p>
 *
 * <p>Пример использования:</p>
 * <pre>
 * MinesweeperField field = new MinesweeperField(3, 3);
 * field.addMine(1, 1); // мина в центре
 * field.addMine(3, 3); // мина в правом нижнем углу
 * field.print();
 * </pre>
 */

public class MinesweeperField {
    private final int rows;
    private final int cols;
    private final int[][] field;

    public MinesweeperField(int rows, int cols) {
        if (rows < 1 || cols < 1) {
            throw new IllegalArgumentException("Размеры поля должны быть положительными");
        }
        this.rows = rows;
        this.cols = cols;
        this.field = new int[rows][cols];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] str = reader.readLine().split(" ");
        int n = Integer.parseInt(str[0]);
        int m = Integer.parseInt(str[1]);
        int k = Integer.parseInt(str[2]);

        MinesweeperField minesweeperField = new MinesweeperField(n, m);
        minesweeperField.fill();

        for (int i = 0; i < k; i++) {
            int[] coords = parseCoordinates(reader.readLine());
            minesweeperField.addMine(coords[0] - 1, coords[1] - 1);
        }

        minesweeperField.print();
        reader.close();
        writer.close();
    }

    private static int[] parseCoordinates(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Некорректная строка координат: " + line);
        }
        return new int[] {
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1])
        };
    }

    public void addMine(int i, int j) {
        field[i][j] = -1;
        fillIncrement(i, j);
    }

    private void fill() {
        for (int i = 0; i < rows; i++) {
            Arrays.fill(field[i], 0);
        }
    }

    private void fillIncrement(int i, int j) {
        int[][] nums = {
                {i - 1, j - 1}, {i - 1, j}, {i - 1, j + 1},
                {i, j - 1},                 {i, j + 1},
                {i + 1, j - 1}, {i + 1, j}, {i + 1, j + 1}
        };

        for (int[] pos : nums) {
            if (include(field, pos[0], pos[1]) && field[pos[0]][pos[1]] != -1) {
                field[pos[0]][pos[1]]++;
            }
        }
    }

    private static boolean include(int[][] arr, int i, int j) {
        return i >= 0 && i < arr.length && j >= 0 && j < arr[i].length;
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(field[i][j] == -1 ? '*' : field[i][j]);
                if (j < cols - 1) System.out.print(' ');
            }
            System.out.println();
        }
    }
}