package intuit.combinatorics.set;

import java.util.LinkedList;
import java.util.List;

/**
 * Находит простые числа от 1 до n.
 */
public class Sieve {

    public static List<Integer> findEasyNumbers(int n) {
        List<Integer> nums = new LinkedList<>();
        for (int i = 2; i <= n; i++) {
            nums.add(i);
        }

        int i = 0;
        while (i < nums.size()) {
            for (int i1 = i + 1; i1 < nums.size(); i1++) {
                Integer num = nums.get(i1);
                if (num % nums.get(i) == 0) {
                    nums.remove(num);
                }
            }
            i++;
        }
        return nums;
    }
}
