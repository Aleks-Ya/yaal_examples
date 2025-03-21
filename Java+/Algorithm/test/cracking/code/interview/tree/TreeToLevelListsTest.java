package cracking.code.interview.tree;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class TreeToLevelListsTest {
    @Test
    void onlyRoot() {
        TreeToLevelLists.Node root = new TreeToLevelLists.Node();
        List<LinkedList<TreeToLevelLists.Node>> lists = TreeToLevelLists.treeToLists(root);
        assertThat(lists, hasSize(1));
        assertThat(lists.get(0), contains(root));
    }

    @Test
    void twoLevels() {
        TreeToLevelLists.Node left = new TreeToLevelLists.Node();
        TreeToLevelLists.Node right = new TreeToLevelLists.Node();
        TreeToLevelLists.Node root = new TreeToLevelLists.Node();
        root.left = left;
        root.right = right;

        List<LinkedList<TreeToLevelLists.Node>> lists = TreeToLevelLists.treeToLists(root);
        assertThat(lists, hasSize(2));
        assertThat(lists.get(0), contains(root));
        assertThat(lists.get(1), contains(left, right));
    }

    @Test
    void threeLevels() {
        TreeToLevelLists.Node left = new TreeToLevelLists.Node();
        TreeToLevelLists.Node rightA = new TreeToLevelLists.Node();
        TreeToLevelLists.Node right = new TreeToLevelLists.Node();
        right.right = rightA;
        TreeToLevelLists.Node root = new TreeToLevelLists.Node();
        root.left = left;
        root.right = right;

        List<LinkedList<TreeToLevelLists.Node>> lists = TreeToLevelLists.treeToLists(root);
        assertThat(lists, hasSize(3));
        assertThat(lists.get(0), contains(root));
        assertThat(lists.get(1), contains(left, right));
        assertThat(lists.get(2), contains(rightA));
    }

}