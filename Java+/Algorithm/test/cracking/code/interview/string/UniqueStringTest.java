package cracking.code.interview.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UniqueStringTest {
    @Test
    void isUnique() {
        assertTrue(UniqueString.isUnique("abcd"));
        assertTrue(UniqueString.isUnique(""));
    }

    @Test
    void isNotUnique() {
        assertFalse(UniqueString.isUnique("abcad"));
        assertFalse(UniqueString.isUnique("aa"));
        assertFalse(UniqueString.isUnique("abccd"));
    }

}