package cracking.code.interview.tree;

import cracking.code.interview.tree.BinaryTreeDepth.Node;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BinaryTreeDepthTest {

    @Test
    public void emptyTree() {
        Node root = new Node();
        assertTrue(BinaryTreeDepth.isBalanced(root));
    }

    @Test
    public void unbalancedTree() {
        Node rightAA = new Node();
        Node rightA = new Node(null, rightAA);
        Node right = new Node(null, rightA);
        Node left = new Node();
        Node root = new Node(left, right);

        assertFalse(BinaryTreeDepth.isBalanced(root));
    }

    @Test
    public void balancedTree() {
        Node rightAA = new Node();
        Node rightA1 = new Node(null, rightAA);
        Node rightA2 = new Node(null, rightAA);
        Node right = new Node(rightA2, rightA1);
        Node leftA = new Node();
        Node left = new Node(leftA, null);
        Node root = new Node(left, right);

        assertTrue(BinaryTreeDepth.isBalanced(root));
    }
}