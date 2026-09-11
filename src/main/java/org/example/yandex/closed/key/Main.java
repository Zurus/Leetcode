package org.example.yandex.closed.key;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.yandex.nod_nok.Main.nod;
import static org.example.yandex.nod_nok.Main.nok;

/**
 * Во всех крупных IT-компаниях немалое внимание уделяется вопросам информационной безопасности, и Яндекс не является исключением.
 * <p>
 * Дима и Егор разрабатывают новый сервис YD (Yandex Dorogi) и в данный момент занимаются аудитом его безопасности. Для шифрования пользовательских данных в YD используется алгоритм шифрования с открытым ключом YS (Yandex Shifrovatel).
 * <p>
 * Схема работы алгоритма YS такова: для каждого сервиса генерируется закрытый ключ (p, q), где p и q — натуральные числа. По закрытому ключу (p, q) генерируется открытый ключ (НОД(p, q), НОК(p, q)), который доступен всем пользователям. Если злоумышленник сможет по открытому ключу получить закрытый ключ, то он получит доступ ко всем данным YD и нанесёт сервису непоправимый вред. Конечно же, Егор и Дима не хотят этого допустить, поэтому они хотят сделать так, чтобы злоумышленнику пришлось перебрать очень много вариантов открытого ключа, прежде чем он сможет его угадать.
 * <p>
 * Дима уже сгенерировал закрытый ключ для YD и получил на его основе открытый ключ (x, y). Егору сразу же стало интересно, сколько вариантов закрытого ключа придётся перебрать злоумышленнику для взлома YD в худшем случае, иными словами, сколько существует закрытых ключей (p, q) таких, что открытым ключом для них является (x, y). К сожалению, у Егора есть много других задач, очень важных для запуска YD, поэтому он просит вас вычислить это количество за него.
 * <p>
 * Формат ввода
 * В первой строке содержатся два целых числа x и y (1 ≤ x ≤ y ≤ 10^12) — описание открытого ключа.
 * <p>
 * Формат вывода
 * Выведите одно целое число — количество закрытых ключей, для которых данный ключ является открытым.
 * <p>
 * Примечание
 * В первом примере существует два закрытых ключа, для которых (5, 10) является открытым ключом: (5, 10) и (10, 5).
 * <p>
 * Во втором примере Дима ошибся, потому что ни один закрытый ключ не порождает открытый ключ (10, 11).
 * <p>
 * В третьем примере подходящими закрытыми ключами являются (527, 9486), (1054, 4743), (4743, 1054), (9486, 527).
 * <p>
 * НОД (наибольшим общим делителем) двух натуральных чисел p и q называется наибольшее число k такое, что p делится на k и q делится на k. Например, НОД(6, 15) равен 3, а НОД(16, 8) равен 8.
 * <p>
 * НОК (наименьшим общим кратным) двух натуральных чисел p и q называется наименьшее число k такое, что k делится на p и k делится на q. Например, НОК(2, 3) равен 6, а НОК(10, 20) равен 20.
 */
public class Main {
    //todo: Задачу так и не решил. Не понял доказательства решения
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int nod = Integer.parseInt(reader.readLine());
        int nok = Integer.parseInt(reader.readLine());
        int a = calc(nok / nod, nod, nok, 0);
        System.out.println("a = " + a + " b= " + nod * nok / a);
        int counter = show(Math.max(a, nok * nod / a), Math.min(nok * nod / a, a), nod, nok);
        System.out.println(counter);
        reader.close();
        writer.close();
    }


    public static int calc(int a, int nod, int nok, int counter) {
        if (nod * nok % a == 0 && (nod(nok * nok / a, a) == nod)) {
            return a;
        }
        return calc(++a, nod, nok, counter + 1);
    }

    public static int show(int a, int b, int nod, int nok) {
        int counter = 0;
        //int newNod = ;
        while (nod(a, b) == nod && nok(a, b) == nok) {
            System.out.println("nod = " + nod + " newNod = " + nod(a, b));
            int temp = b;
            b = a - b;
            a = temp;
            counter+=2;
        }
        return counter;
    }

//    public static int calc(int ab, int a) {
//        if (ab % a != 0) {
//            return calc(ab, a - 1);
//        }
//        int b = ab / a;
//        return ab / a;
//    }

//    public static int nod(int a, int b) {
//        List<Integer> listA = factorize(a);
//        List<Integer> listB = factorize(b);
//        List<Integer> result = new ArrayList<>();
//
//        for (int i = 0; i < listA.size(); i++) {
//            int x = listA.get(i);
//            if (listB.contains(x)) {
//                result.add(x);
//                listB.remove(Integer.valueOf(x));
//            }
//        }
//
//
//        int nodValue = 1;
//        for (int num : result) {
//            nodValue *= num;
//        }
//        return nodValue;
//    }
//
//    public static int nok(int a, int b) {
//        List<Integer> listA = factorize(a);
//        List<Integer> listB = factorize(b);
//        List<Integer> result = new ArrayList<>();
//
//        result.addAll(listA);
//        result.addAll(listB);
//
//        // Удаляем общие множители (по одному вхождению каждого)
//        for (int i = 0; i < listA.size(); i++) {
//            int x = listA.get(i);
//            if (listB.contains(x)) {
//                result.remove(Integer.valueOf(x));   // удаляем одну такую цифру из объединения
//                listB.remove(Integer.valueOf(x));    // удаляем из listB, чтобы не учитывать повторно
//            }
//        }
//
//        int nokValue = 1;
//        for (int num : result) {
//            nokValue *= num;
//        }
//        return nokValue;
//    }

    private static List<Integer> factorize(int n) {
        List<Integer> factors = new ArrayList<>();
        for (int i = 2; i <= n / i; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        if (n > 1) {
            factors.add(n);
        }
        return factors;
    }
}
