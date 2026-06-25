package org.example.yandex;


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
}
