package org.example.yandex.graph;

import org.example.yandex.graph.deepsearch.DeepSearch;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Main {

    private static Queue<DeepSearch> children = new ArrayDeque<>();
    private static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        Integer[] data = splitString(reader.readLine());
        int n = data[0];
        int m = data[1];
        DeepSearch[] graphs = new DeepSearch[n];
        for (int i = 0; i < n; i++) {
            graphs[i] = new DeepSearch(i + 1);
        }

        for (int i = 0; i < m; i++) {
            data = splitString(reader.readLine());
            graphs[data[0] - 1].add(graphs[data[1] - 1]);
        }

        visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            DeepSearch deepSearch = graphs[i];
            if (!visited[i]) {
                deepSearch(deepSearch);
            }
        }
        int count = 0;
        for (int i = 0; i < visited.length; i++) {
            if (visited[i]) count++;
        }
        System.out.println(count);
        for (int i = 0; i < visited.length; i++) {
            if (visited[i]) {
                System.out.print(i + 1 + " ");
            }
        }

        reader.close();
        writer.close();
    }

    private static void deepSearch(DeepSearch deepSearch) {
        if (!visited[deepSearch.getIdx() - 1] && !deepSearch.isEmpty()) {
            visited[deepSearch.getIdx() - 1] = true;
            deepSearch.getNeighbors().forEach(Main::deepSearch);
        }
    }

    public static Integer[] splitString(String data) {
        return Arrays.stream(data.trim()
                        .split("\\s+"))
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
    }

}
