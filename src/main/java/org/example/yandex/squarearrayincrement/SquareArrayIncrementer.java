package org.example.yandex.squarearrayincrement;


/**
 * Дан массив NxN
 * <p>
 * Задается точка по координатам
 * Вокруг этой точки числа нужно увеличивать на +1
 */
public class SquareArrayIncrementer {

    private final int n;
    private final int[][] field;

    public SquareArrayIncrementer(int n) {
        this.n = n;
        this.field = new int[n][n];
    }


    public void incrementAroundPoint(int i, int j) {
        for (int k = i - 1; k <= i + 1; k++) {
            for (int l = j - 1; l <= j + 1; l++) {
                if (k == i && l == j) continue;
                if (k < 0 || k >= field.length || l < 0 || l >= field[k].length) {
                    continue;
                }
                field[k][l] += 1;
            }
        }
    }

    public int[][] getField() {
        return field;
    }
}
