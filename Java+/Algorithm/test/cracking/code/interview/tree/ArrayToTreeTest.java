package cracking.code.interview.tree;

import org.junit.jupiter.api.Test;

/**
 * @author Yablokov Aleksey
 */
public class ArrayToTreeTest {
    @Test
    void toNode() {
        int[] arr = new int[]{3, 7, 14, 18, 21, 26};
        ArrayToTree.Node node = ArrayToTree.toNode(arr);
        System.out.println(node);
    }

}