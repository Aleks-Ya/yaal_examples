package cracking.code.interview.string;

/**
 * Implement an algorithm to determine if a string has all unique characters. What
 * if you cannot use additional data structures?
 */
class UniqueString {

    static boolean isUnique(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars.length; j++) {
                if (i == j) {
                    continue;
                }
                if (chars[i] == chars[j]) {
                    return false;
                }
            }
        }
        return true;
    }

}
