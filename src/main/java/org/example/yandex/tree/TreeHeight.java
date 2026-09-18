package org.example.yandex.tree;

import java.io.*;
import java.util.StringTokenizer;

/*
 * Реализуйте бинарное дерево поиска для целых чисел.
 *
 * Элементы добавляются в дерево последовательно, балансировки не происходит
 * (первый элемент последовательности будет являться корнем дерева).
 * Найдите высоту получившегося дерева.
 * Каждое уникальное число должно встречаться в дереве ровно один раз.
 *
 * Формат ввода:
 * На вход программа получает последовательность натуральных чисел.
 * Последовательность завершается числом 0, которое означает конец ввода.
 * Добавлять его в дерево не надо.
 *
 * Формат вывода:
 * Выведите высоту получившегося дерева.
 *
 * Примечание:
 * Бинарное дерево поиска — дерево, обладающее следующими свойствами:
 * 1. Все ключи в дереве различны.
 * 2. У каждой вершины не более двух детей.
 * 3. Все вершины обладают ключами, на которых определена операция сравнения
 *    (в данной задаче ключами являются целые числа).
 * 4. У всех вершин левого поддерева вершины v ключи меньше, чем ключ v.
 * 5. У всех вершин правого поддерева вершины v ключи больше, чем ключ v.
 * 6. Оба поддерева — левое и правое — являются двоичными деревьями поиска.
 */
public class TreeHeight {

    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            StringTokenizer st = new StringTokenizer(reader.readLine());
            int idx = 0;
            TreeNode treeNode = new TreeNode();
            while (st.hasMoreTokens() && idx < st.countTokens()) {
                int val = Integer.parseInt(st.nextToken());
                if (val == 0) {
                    break;
                }
                treeNode.addNode(val);
            }

            writer.write(String.valueOf(treeNode.getHeight()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class TreeNode {
        private Node head;
        private int height; // высота в вершинах: 0 для пустого дерева, 1 для одного узла

        public TreeNode() {
            this.height = 0;
        }

        public void addNode(int val) {
            if (head == null) {
                head = new Node(val);
                height = 1;
                return;
            }
            int insertedLevel = head.addNode(val, 1);
            if (insertedLevel > height) {
                height = insertedLevel;
            }
        }

        public int getHeight() {
            return height;
        }
    }

    private static class Node {

        private final int val;
        private Node left;
        private Node right;

        public Node(int val) {
            this.val = val;
        }

        /**
         * Рекурсивно вставляет значение в поддерево.
         *
         * @param val   вставляемое значение
         * @param level уровень текущего узла (корень дерева — 1)
         * @return уровень, на котором был создан новый узел,
         *         или -1, если значение уже есть в дереве
         */
        public int addNode(int val, int level) {
            if (val < this.val) {
                if (left == null) {
                    left = new Node(val);
                    return level + 1;
                }
                return left.addNode(val, level + 1);
            } else if (val > this.val) {
                if (right == null) {
                    right = new Node(val);
                    return level + 1;
                }
                return right.addNode(val, level + 1);
            } else {
                // дубликат — не добавляем
                return -1;
            }
        }
    }
}
