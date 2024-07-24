package org.example.bit;

import org.example.utils.StringUtils;

public class Bit {

    public static void main(String[] args) {
        int num = 10;
        while (true) {
            System.out.println(StringUtils.toBinaryString(num));
            num = num >> 1;
            if (num == 0) break;
        }
    }
}
