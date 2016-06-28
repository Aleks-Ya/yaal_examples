package cracking.code.interview;

import java.util.LinkedList;
import java.util.List;

/**
 * Given a binary tree, design an algorithm which creates a linked list of all the nodes at
 * each depth (e.g., if you have a tree with depth D, you'll have D linked lists).
 */
class TreeToLevelLists {

    static List<LinkedList<Node>> treeToLists(Node root) {
        List<LinkedList<Node>> res = new LinkedList<>();
        LinkedList<Node> parents = new LinkedList<>();
        parents.add(root);
        res.add(parents);
        do {
            LinkedList<Node> level = levelToList(parents);
            if (!level.isEmpty()) {
                res.add(level);
                parents = level;
            } else {
                break;
            }
        } while (true);
        return res;
    }

    private static LinkedList<Node> levelToList(List<Node> parents) {
        LinkedList<Node> res = new LinkedList<>();
        for (Node parent : parents) {
            if (parent.left != null) {
                res.add(parent.left);
            }
            if (parent.right != null) {
                res.add(parent.right);
            }
        }
        return res;
    }

    static class Node {
        Node left;
        Node right;
    }
}
