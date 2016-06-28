package cracking.code.interview.string;

/**
 * Implement a method to perform basic string compression using the counts of
 * repeated characters. For example, the string aabcccccaaa would become
 * a2blc5a3. If the "compressed" string would not become smaller than the original
 * string, your method should return the original string.
 */
class CompressString {

    static String compress(String s) {
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        int j = 0;
        for (int i = 0; i < chars.length; i = j) {
            char cur = chars[i];
            j = i + 1;
            while (j < chars.length && cur == chars[j]) {
                j++;
            }
            sb.append(cur).append(j - i);
        }
        return s.length() > sb.length() ? sb.toString() : s;
    }
}
