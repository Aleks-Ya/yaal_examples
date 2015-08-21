package intuit.combinatorics.tree;

/**
 * Узел бинарного дерева.
 */
class BiNode {
    public BiNode(Object value) {
        this.value = value;
    }

    public BiNode(Object value, BiNode left, BiNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    Object value;
    BiNode left;
    BiNode right;
}
