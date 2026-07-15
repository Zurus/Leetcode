package org.example.array;

import java.io.*;
import java.util.Arrays;

public class PrisonIf {

    private static final int N = 5;

    private static final int A = 0;
    private static final int B = 1;
    private static final int C = 2;
    private static final int D = 3;
    private static final int E = 4;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int A = Integer.parseInt(reader.readLine());
        int B = Integer.parseInt(reader.readLine());
        int C = Integer.parseInt(reader.readLine());
        int D = Integer.parseInt(reader.readLine());
        int E = Integer.parseInt(reader.readLine());
        reader.close();

        int[] ABC = {A, B, C};
        Arrays.sort(ABC);
        int[] wall = {D, E};
        Arrays.sort(wall);

        String res = "NO";
        if (wall[0] >= ABC[0] && wall[1] >= ABC[1]) {
            res = "YES";
        }


        writer.write(res);
        writer.close();
    }
}
