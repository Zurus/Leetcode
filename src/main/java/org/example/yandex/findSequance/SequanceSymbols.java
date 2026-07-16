package org.example.yandex.findSequance;

import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class SequanceSymbols {


    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder output = new StringBuilder();

        String data;
        Map<String, Integer> elements = new TreeMap<>();
        while ((data = reader.readLine())!=null) {


            StringTokenizer stringTokenizer = new StringTokenizer(data);
            while (stringTokenizer.hasMoreTokens()) {
                String toka = stringTokenizer.nextToken();
                int count = elements.getOrDefault(toka, 0);
                output.append(count).append(' ');
                elements.put(toka, count + 1);
            }
        }
        reader.close();
        if (output.length() > 0) {
            output.setLength(output.length() - 1);
        }
        System.out.print(output);

        writer.close();
    }
}
