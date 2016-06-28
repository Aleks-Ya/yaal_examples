package cracking.code.interview.string;

/**
 * Given two strings, write a method to decide if one is a permutation of the other.
 */
class PermutationStrings {

    static boolean isPermutations(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        boolean[] exists = new boolean[65536];
        for (char c : s1.toCharArray()) {
            exists[c] = true;
        }
        for (char c : s2.toCharArray()) {
            if (!exists[c]) {
                return false;
            }
        }
        return true;
    }
}
