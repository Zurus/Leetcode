package org.example.array;

public class A {
    private A next;
    private int val;

    public A(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val + "";
    }

    public String getAllVal() {
        return this.next.fullList();
    }

    public String fullList() {
        return val + (!isLast() ? next.fullList() : "");
    }

    public void add(A next) {
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

    public void reverse(A prev, A head) {

        if (this.isLast()) {
            this.next = prev;
            head.next = this;
            return;
        }

        this.next.reverse(this, head);
        this.next = prev;
    }
}
