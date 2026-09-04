package org.example.yandex.nod_nok;

//https://algorithmica.org/ru/euclid
//https://lc.rt.ru/classbook/matematika-6-klass/delimost-chisel-profilnyi-uroven/6612
//https://spravochnick.ru/matematika/algoritm_evklida/#algoritm-evklida-dokazatelstvo
//https://education.yandex.ru/handbook/algorithms/article/vychislenie-nok-i-nod#klyuchevye-voprosy-paragrafa
public class Main {


    public static int nod(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static int nok(int a, int b) {
        return (a / nod(a, b)) * b;
    }
}
