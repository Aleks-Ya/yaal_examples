package intuit.combinatorics.split;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

class LetterCombinations {
    public static Set<String> combine(String word) {
        assert word != null;
        Queue<String> queue = new ArrayDeque<>();
        Set<String> processed = new HashSet<>();
        queue.add(word);
        while (!queue.isEmpty()) {
            process(queue, processed);
        }
        return processed;
    }

    private static void process(Queue<String> queue, Set<String> processed) {
        assert !queue.isEmpty();
        String word = queue.poll();
        char[] chars = word.toCharArray();
        processed.add(word);
        for (int i = 0; i < chars.length; i++) {
            char curr = chars[i];
            for (int j = i + 1; j < chars.length; j++) {
                char sec = chars[j];
                if (curr != sec) {
                    chars[i] = sec;
                    chars[j] = curr;
                    String variant = new String(chars);
                    if (!processed.contains(variant) && !queue.contains(variant)) {
                        queue.add(variant);
                    }
                    chars[j] = sec;
                    chars[i] = curr;
                }
            }
        }
    }
}
