package intuit.combinatorics.tree.callback;

import intuit.combinatorics.tree.BiNode;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Алгоритм обхода бинарного дерева в глубину.
 */
public class InDeepTraversalTest {

    @Test
    void oneLevelTree() {
        BiNode<Character> left = new BiNode<>('L');
        BiNode<Character> right = new BiNode<>('R');
        BiNode<Character> root = new BiNode<>('A', left, right);
        StringBuilder sb = new StringBuilder();
        InDeepTraversal.inDeep(root, node -> sb.append(node.value));
        assertThat(sb.toString(), equalTo("ALR"));
    }

    @Test
    void twoLevelTree() {
        BiNode<String> left2 = new BiNode<>("L2");
        BiNode<String> right2 = new BiNode<>("R2");
        BiNode<String> left1 = new BiNode<>("L1", left2, right2);
        BiNode<String> right1 = new BiNode<>("R1");
        BiNode<String> root = new BiNode<>("A0", left1, right1);
        StringBuilder sb = new StringBuilder();
        InDeepTraversal.inDeep(root, node -> sb.append(node.value));
        assertThat(sb.toString(), equalTo("A0L1L2R2R1"));
    }
}