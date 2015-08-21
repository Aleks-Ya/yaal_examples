package intuit.combinatorics.tree;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Алгоритм обхода бинарного дерева в глубину.
 */
public class InDeepTraversalTest {

    @Test
    public void oneLevelTree() {
        BiNode left = new BiNode("L");
        BiNode right = new BiNode("R");
        BiNode root = new BiNode("A", left, right);
        StringBuilder sb = new StringBuilder();
        InDeepTraversal.inDeep(root, node -> sb.append(node.value));
        assertThat(sb.toString(), equalTo("ALR"));
    }

    @Test
    public void twoLevelTree() {
        BiNode left2 = new BiNode("L2");
        BiNode right2 = new BiNode("R2");
        BiNode left1 = new BiNode("L1", left2, right2);
        BiNode right1 = new BiNode("R1");
        BiNode root = new BiNode("A0", left1, right1);
        StringBuilder sb = new StringBuilder();
        InDeepTraversal.inDeep(root, node -> sb.append(node.value));
        assertThat(sb.toString(), equalTo("A0L1L2R2R1"));
    }
}