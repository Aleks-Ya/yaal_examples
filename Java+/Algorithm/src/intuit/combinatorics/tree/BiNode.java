package intuit.combinatorics.tree;

/**
 * Узел бинарного дерева.
 */
public class BiNode<E> {
    public BiNode(E value) {
        this(value, null, null);
    }

    public BiNode(E value, BiNode<E> left, BiNode<E> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public final E value;
    public final BiNode<E> left;
    public final BiNode<E> right;

    @Override
    public String toString() {
        return value.toString();
    }
}
