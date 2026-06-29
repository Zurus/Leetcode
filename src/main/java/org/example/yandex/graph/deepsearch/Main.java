package org.example.yandex.graph.deepsearch;

import java.io.*;
import java.util.*;

public class Main {

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
            graphs[data[0] - 1].add(graphs[data[1] - 1], true);
        }

        visited = new boolean[n];

        DeepSearch deepSearch = graphs[0];
        deepSearch(deepSearch);
        visited[0] = true;
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
        System.out.println();
        reader.close();
        writer.close();
    }

    private static void deepSearch(DeepSearch deepSearch) {
        if (!visited[deepSearch.getIdx() - 1] && !deepSearch.isEmpty()) {
            visited[deepSearch.getIdx() - 1] = true;
            List<DeepSearch> list = deepSearch.getNeighbors();
            for (int i = 0; i < list.size(); i++) {
                DeepSearch neib = list.get(i);
                if (deepSearch.isSameDeep(neib)) continue;
                deepSearch(neib);
            }
        }
    }

    public static Integer[] splitString(String data) {
        return Arrays.stream(data.trim()
                        .split("\\s+"))
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
    }

    public static class DeepSearch {
        private int idx;
        private List<DeepSearch> neighbors;

        public DeepSearch(int idx) {
            this.idx = idx;
            neighbors = new ArrayList<>();
        }

        public int getIdx() {
            return idx;
        }

        public void add(DeepSearch deepSearch, boolean recursive) {
            neighbors.add(deepSearch);
            if (recursive && !isSameDeep(deepSearch)) {
                deepSearch.add(this, false);
            }
        }

        public void add(DeepSearch deepSearch) {
            add(deepSearch, false);
        }

        public List<DeepSearch> getNeighbors() {
            return neighbors;
        }

        public boolean isEmpty() {
            return neighbors.isEmpty();
        }

        public boolean isSameDeep(DeepSearch deepSearch) {
            return idx == deepSearch.getIdx();
        }
    }
}
