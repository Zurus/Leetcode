package org.example.array;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class B {
    private A node;

    public B() {
        node = new A(0);
    }

    public void print() {
        System.out.println(node.getAllVal());
    }

    public void fill() {
        for (int i = 1; i < 4; i++) {
            node.add(new A(i));
        }
    }

    public void reverse() {
        node.reverse();
    }
}
