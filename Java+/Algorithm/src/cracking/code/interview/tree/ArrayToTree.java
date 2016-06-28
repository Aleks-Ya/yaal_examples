package cracking.code.interview.tree;

/**
 * Given a sorted (increasing order) array with unique integer elements, write an
 * algorithm to create a binary search tree with minimal height.
 */
class ArrayToTree {

    static Node toNode(int[] arr) {
        return toNode(arr, 0, arr.length - 1);
    }

    private static Node toNode(int[] arr, int left, int right) {
        if (left > right) {
            return null;
        }
        if (left == right) {
            return new Node(arr[left], null, null);
        }
        int mid = (right - left + 1) / 2 + left;
        Node node1 = toNode(arr, left, mid - 1);
        Node node2 = toNode(arr, mid + 1, right);
        return new Node(arr[mid], node1, node2);
    }

    static class Node {
        Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        int value;
        Node left;
        Node right;

        @Override
        public String toString() {
            return "Node{" +
                    value +
                    ", " + (left != null ? left.toString() : "") +
                    ", " + (right != null ? right.toString() : "") +
                    '}';
        }
    }
}
