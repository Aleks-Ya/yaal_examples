package intuit.combinatorics;

interface PriorityQueue<E> {
    void put(E element, int priority);

    E get();
}
