package intuit.combinatorics.tree;

class InDeepTraversal {
    public static void inDeep(BiNode root, Callback callback) {
        callback.work(root);
        if (root.left != null) {
            if (isLeaf(root.left)) {
                callback.work(root.left);
            } else {
                inDeep(root.left, callback);
            }
        }
        if (root.right != null) {
            if (isLeaf(root.right)) {
                callback.work(root.right);
            } else {
                inDeep(root.right, callback);
            }
        }
    }

    private static boolean isLeaf(BiNode node) {
        assert node != null;
        return node.left == null && node.right == null;
    }

    interface Callback {
        void work(BiNode node);
    }
}
