package org.example.linked;


public class Linked {
    private Node node;

    public Linked() {
        node = new Node(0);
    }

    public void print() {
        System.out.println(node.getAllVal());
    }

    public void fill() {
        for (int i = 1; i < 4; i++) {
            node.add(new Node(i));
        }
    }

    public void reverse() {
        node.reverse();
    }

    private static class Node {
        private Node next;
        private int val;

        public Node(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return String.valueOf(val);
        }

        public String getAllVal() {
            return this.next.fullList();
        }

        public String fullList() {
            return val + (!isLast() ? next.fullList() : "");
        }

        public void add(Node next) {
            if (this.isLast()) {
                this.next = next;
            } else {
                this.next.add(next);
            }
        }

        public Boolean isLast() {
            return next == null;
        }

        public void reverse() {
            this.next.reverse(null, this);
        }

        public void reverse(Node prev, Node head) {
            if (this.isLast()) {
                this.next = prev;
                head.next = this;
                return;
            }

            this.next.reverse(this, head);
            this.next = prev;
        }
    }
}
