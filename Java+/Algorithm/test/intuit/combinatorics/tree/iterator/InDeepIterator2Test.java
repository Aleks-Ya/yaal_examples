package intuit.combinatorics.tree.iterator;

import intuit.combinatorics.tree.BiNode;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Алгоритм обхода бинарного дерева в глубину.
 */
public class InDeepIterator2Test {

    @Test
    public void zeroLevelTree() {
        BiNode<Character> root = new BiNode<>('A', null, null);
        assertThat(treeToString(root), equalTo("A"));
    }

    @Test
    public void oneLevelTree() {
        BiNode<Character> left = new BiNode<>('L');
        BiNode<Character> right = new BiNode<>('R');
        BiNode<Character> root = new BiNode<>('A', left, right);

        assertThat(treeToString(root), equalTo("ALR"));
    }

    private String treeToString(BiNode<Character> root) {
        StringBuilder sb = new StringBuilder();
        Iterator<Character> iterator = new InDeepIterator<>(root);
        while (iterator.hasNext()) {
            sb.append(iterator.next());
        }
        return sb.toString();
    }

//    @Test
//    public void twoLevelTree() {
//        BiNode left2 = new BiNode("L2");
//        BiNode right2 = new BiNode("R2");
//        BiNode left1 = new BiNode("L1", left2, right2);
//        BiNode right1 = new BiNode("R1");
//        BiNode root = new BiNode("A0", left1, right1);
//        StringBuilder sb = new StringBuilder();
//        InDeepIterator.next(root, node -> sb.append(node.value));
//        assertThat(sb.toString(), equalTo("A0L1L2R2R1"));
//    }
}