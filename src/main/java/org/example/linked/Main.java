package org.example.linked;


public class Main {

    public static void main(String[] args) {
        System.out.println(24345 & 15);
        Linked linked = new Linked();

        linked.fill();
        linked.print();
        linked.reverse();
        linked.print();
    }
}
