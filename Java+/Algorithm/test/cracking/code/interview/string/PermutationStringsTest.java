package cracking.code.interview.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PermutationStringsTest {
    @Test
    void yes() {
        assertTrue(PermutationStrings.isPermutations("abcd", "dbca"));
        assertTrue(PermutationStrings.isPermutations("", ""));
    }

    @Test
    void no() {
        assertFalse(PermutationStrings.isPermutations("a", "ab"));
        assertFalse(PermutationStrings.isPermutations("abb", "ab"));
    }
}