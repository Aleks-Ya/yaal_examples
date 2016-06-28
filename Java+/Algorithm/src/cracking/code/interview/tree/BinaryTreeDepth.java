package cracking.code.interview.tree;

/**
 * Implement a function to check if a binary tree is balanced. For the purposes of this
 * question, a balanced tree is defined to be a tree such that the heights of the two
 * subtrees of any node never differ by more than on.
 */
class BinaryTreeDepth {
    private static final int NOT_BALANCED = -1;

    static boolean isBalanced(Node root) {
        return height(root) != NOT_BALANCED;
    }

    private static int height(Node node) {
        int leftHeight = node.left != null ? height(node.left) : 0;
        if (leftHeight == NOT_BALANCED) {
            return NOT_BALANCED;
        }
        int rightHeight = node.right != null ? height(node.right) : 0;
        if (rightHeight == NOT_BALANCED) {
            return NOT_BALANCED;
        }
        int diff = Math.abs(leftHeight - rightHeight);
        if (diff <= 1) {
            return Math.max(leftHeight, rightHeight) + 1;
        } else {
            return NOT_BALANCED;
        }
    }

    static class Node {
        Node() {
        }

        Node(Node left, Node right) {
            this.left = left;
            this.right = right;
        }

        Node left;
        Node right;

        @Override
        public String toString() {
            return "Node{" +
                    left +
                    ", " + right +
                    '}';
        }
    }
}
