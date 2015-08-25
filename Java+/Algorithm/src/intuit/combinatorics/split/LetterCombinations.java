package intuit.combinatorics.split;

import java.util.ArrayList;
import java.util.List;

class LetterCombinations {
    public static List<String> combine(String word) {
        assert word != null;
        List<String> res = new ArrayList<>();
        process(word, res);
        return res;
    }

    private static void process(String word, List<String> res) {
        if (!res.contains(word)) {
            res.add(word);
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char curr = chars[i];
                for (int j = 0; j < chars.length; j++) {
                    char sec = chars[j];
                    if (curr != sec) {
                        chars[i] = sec;
                        chars[j] = curr;
                        String variant = new String(chars);
                        chars[j] = sec;
                        chars[i] = curr;
                        process(variant, res);
                    }
                }
            }
        }
    }
}
