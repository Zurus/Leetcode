package org.example.yandex.squarearrayincrement;

import org.example.utils.ArrayUtils;

public class Main {

    public static void main(String[] args) {
        SquareArrayIncrementer squareArrayIncrementer = new SquareArrayIncrementer(3);
        System.out.println("---------------------------");
        ArrayUtils.printArray(squareArrayIncrementer.getField());
        System.out.println("---------------------------");
        squareArrayIncrementer.incrementAroundPoint(0,0);
        ArrayUtils.printArray(squareArrayIncrementer.getField());
    }
}
