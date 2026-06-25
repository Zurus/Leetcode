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

    private static final int MINE = -1;

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
        if (field[i][j] != MINE) {
            field[i][j] = MINE;
            fillIncrement(i, j);
        }
    }

    private void fillIncrement(int i, int j) {
        for (int[] pos : nums) {
            if (include(i + pos[0], j + pos[1]) && field[i + pos[0]][j + pos[1]] != MINE) {
                field[i + pos[0]][j + pos[1]]++;
            }
        }
    }

    private void fillIncrementAlternate(int i, int j) {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int r = i + dr;
                int c = j + dc;
                if (r >= 0 && r < rows && c >= 0 && c < cols && field[r][c] != MINE) {
                    field[r][c]++;
                }
            }
        }
    }


    private boolean include(int i, int j) {
        return i >= 0 && i < rows && j >= 0 && j < cols;
    }

    public int[][] getField() {
        return field;
    }
}