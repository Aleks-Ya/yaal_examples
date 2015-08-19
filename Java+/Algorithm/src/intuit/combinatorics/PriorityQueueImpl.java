package intuit.combinatorics;

class PriorityQueueImpl<E> implements PriorityQueue<E> {
    private PriorityNode<E> first;

    @Override
    public void put(E element, int priority) {
        if (first != null) {
            PriorityNode<E> node = first;
            PriorityNode<E> prev = node;
            while (node != null) {
                if (node.priority < priority) {
                    if (node.prev != null) {
                        PriorityNode<E> newNode = new PriorityNode<>(element, priority, node.prev, node);
                        node.prev.next = newNode;
                        node.prev = newNode;
                    } else {
                        PriorityNode<E> newNode = new PriorityNode<>(element, priority, null, node);
                        node.prev = newNode;
                        first = newNode;
                    }
                    return;
                }
                prev = node;
                node = node.next;
            }
            prev.next = new PriorityNode<>(element, priority, prev, null);
        } else {
            first = new PriorityNode<>(element, priority, null, null);
        }
    }

    @Override
    public E get() {
        if (first != null) {
            PriorityNode<E> res = first;
            first = first.next;
            return res.value;
        } else {
            return null;
        }
    }

    static class PriorityNode<E> {
        int priority;
        PriorityNode<E> prev;
        PriorityNode<E> next;
        E value;

        public PriorityNode(E value, int priority, PriorityNode<E> prev, PriorityNode<E> next) {
            this.priority = priority;
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return "PriorityNode{" +
                    "priority=" + priority +
                    ", value=" + value +
                    '}';
        }
    }
}
