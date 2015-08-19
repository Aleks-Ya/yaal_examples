package intuit.combinatorics;

import org.junit.Test;

/**
 * Создать список, элементами которого являются числа: 1 2 3 4 5 6 7 8 9.
 * Вывести список на экран терминала.
 * Включить в связанный список элемент 2005 после каждого элемента, который делится на 3.
 * Модифицированный список вывести на экран терминала.
 */
public class NodeTest {
    @Test
    public void test() {
        //init list
        final Node<Integer> first;
        {
            Node<Integer> node = new Node<>(0, null, null);
            first = node;
            for (int i = 1; i < 10; i++) {
                Node<Integer> newNode = new Node<>(i, node, null);
                node.next = newNode;
                node = newNode;
            }
            System.out.println(nodesToString(first));
        }

        //insert 2005
        {
            Node<Integer> node = first;
            while (node != null) {
                if (node.value % 3 == 0) {
                    node.next = new Node<>(2005, node, node.next);
                }
                node = node.next;
            }
            System.out.println(nodesToString(first));
        }
    }

    private String nodesToString(Node<Integer> first) {
        StringBuilder sb = new StringBuilder();
        Node<Integer> node = first;
        while (node != null) {
            sb.append(node.value).append(" ");
            node = node.next;
        }
        return sb.toString();
    }
}