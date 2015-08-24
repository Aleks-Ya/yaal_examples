package intuit.combinatorics.tree.iterator;

import intuit.combinatorics.tree.BiNode;

import java.util.Iterator;

/**
 * НЕ ЗАВАРЕШЕНО!
 */
class InDeepIterator<E> implements Iterator<E> {
    private BiNode<E> next;

    public InDeepIterator(BiNode<E> root) {
        next = root;
    }

    @Override
    public E next() {
        BiNode<E> current = next;
        if (current.left != null) {
            next = current.left;
        }
        if (current.right != null) {
            next = current.right;
        }
        if (current.left == null && current.right == null) {
            next = next.right;
        }
        return current.value;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }
}
