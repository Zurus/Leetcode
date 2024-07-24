package org.example.array;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class C {

    public static void main(String[] args) {
//        List list;
//
//
//        System.out.println(24345 & 15);
//
//        B b = new B();
//
//        b.fill();
//        b.print();
//        b.reverse();
//        b.print();
//


        System.out.println(Boolean.valueOf(""));
//
        List<Integer> list1 = new LinkedList<>();
        list1.add(1);
        list1.add(1);
        list1.add(1);
        list1.add(2);
        list1.add(3);

        list1.forEach(System.out::println);
        System.out.println();
        Iterator<Integer> iter = list1.iterator();
        Integer prev = iter.next();
        while (iter.hasNext()) {
            Integer curr = iter.next();
            if (prev.equals(curr)) {
                iter.remove();
            } else {
                prev = curr;
            }
        }

        list1.forEach(System.out::println);
    }
}
