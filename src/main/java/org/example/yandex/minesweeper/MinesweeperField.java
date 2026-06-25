package org.example.yandex.minesweeper;


import java.io.*;

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

    private static int[][] nums = {
            {-1, -1}, {-1, 0}, {-1, +1},
            {0, -1}, {0, +1},
            {+1, -1}, {+1, 0}, {+1, +1}
    };

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

    public void addMine(int i, int j) {
        if (field[i][j] != -1) {
            field[i][j] = -1;
            fillIncrement(i, j);
        }
    }

    private void fillIncrement(int i, int j) {
        for (int[] pos : nums) {
            if (include(i + pos[0], j + pos[1]) && field[i + pos[0]][j + pos[1]] != -1) {
                field[i + pos[0]][j + pos[1]]++;
            }
        }
    }

    private boolean include(int i, int j) {
        return i >= 0 && i < rows && j >= 0 && j < cols;
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