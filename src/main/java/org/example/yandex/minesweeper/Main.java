package org.example.yandex.minesweeper;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] str = reader.readLine().split(" ");
        int n = Integer.parseInt(str[0]);
        int m = Integer.parseInt(str[1]);
        int k = Integer.parseInt(str[2]);

        MinesweeperField minesweeperField = new MinesweeperField(n, m);

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
        return new int[]{
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1])
        };
    }
}
