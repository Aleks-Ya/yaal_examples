package cracking.code.interview.tree;

import cracking.code.interview.tree.IsThereRouteBetweenGraphNodes.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsThereRouteBetweenGraphNodesTest {
    private final Node D = new Node();
    private final Node B = new Node();
    private final Node C = new Node();
    private final Node A = new Node();

    public IsThereRouteBetweenGraphNodesTest() {
        D.paths = new Node[]{C};
        B.paths = new Node[]{D};
        C.paths = new Node[]{D};
        A.paths = new Node[]{B, C};
    }

    @Test
    void noPath() {
        assertFalse(IsThereRouteBetweenGraphNodes.hasPath(D, A));
    }

    @Test
    void noPath2() {
        assertFalse(IsThereRouteBetweenGraphNodes.hasPath(D, B));
    }

    @Test
    void hasPath() {
        assertTrue(IsThereRouteBetweenGraphNodes.hasPath(A, D));
    }

    @Test
    void hasPath2() {
        assertTrue(IsThereRouteBetweenGraphNodes.hasPath(B, D));
    }

    @Test
    void hasPath3() {
        assertTrue(IsThereRouteBetweenGraphNodes.hasPath(B, C));
    }
}