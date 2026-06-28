package org.example.yandex.graph.deepsearch;

import java.util.ArrayList;
import java.util.List;

public class DeepSearch {
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
        if (recursive) {
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
}
