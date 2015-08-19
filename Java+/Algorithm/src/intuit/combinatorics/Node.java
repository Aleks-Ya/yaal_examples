package intuit.combinatorics;

class Node<E> {
    Node<E> prev;
    Node<E> next;
    E value;

    public Node(E value, Node<E> prev, Node<E> next) {
        this.prev = prev;
        this.value = value;
        this.next = next;
    }
}
