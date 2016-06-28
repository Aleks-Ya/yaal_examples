package cracking.code.interview;

import org.junit.Test;

/**
 * @author Yablokov Aleksey
 */
public class ArrayToTreeTest {
    @Test
    public void toNode() {
        int[] arr = new int[]{3, 7, 14, 18, 21, 26};
        ArrayToTree.Node node = ArrayToTree.toNode(arr);
        System.out.println(node);
    }

}