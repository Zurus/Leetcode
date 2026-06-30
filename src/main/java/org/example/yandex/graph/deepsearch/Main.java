package org.example.yandex.graph.deepsearch;

import java.io.*;
import java.util.*;

public class Main {

    private static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(reader.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        DeepSearch[] graphs = new DeepSearch[n];
        for (int i = 0; i < n; i++) {
            graphs[i] = new DeepSearch(i + 1);
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(reader.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            graphs[u].addNeighbor(graphs[v]);
            if (u != v) {
                graphs[v].addNeighbor(graphs[u]);
            }
        }

        visited = new boolean[n];

        dfs(graphs[0]);

        int count = 0;

        for (boolean v : visited) {
            if (v) count++;
        }

        System.out.println(count);
        for (int i = 0; i < visited.length; i++) {
            if (visited[i]) {
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println();

        reader.close();
        writer.close();
    }

    private static void dfs(DeepSearch node) {
        int idx = node.getIdx() - 1;
        if (visited[idx]) {
            return;
        }
        visited[idx] = true;
        for (DeepSearch neighbor : node.getNeighbors()) {
            dfs(neighbor);
        }
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

        // Простой метод добавления соседа
        public void addNeighbor(DeepSearch other) {
            neighbors.add(other);
        }

        public List<DeepSearch> getNeighbors() {
            return neighbors;
        }
    }
}
