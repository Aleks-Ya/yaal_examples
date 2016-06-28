package cracking.code.interview.string;

import org.junit.Test;

import static org.junit.Assert.*;

public class UniqueStringTest {
    @Test
    public void isUnique() {
        assertTrue(UniqueString.isUnique("abcd"));
        assertTrue(UniqueString.isUnique(""));
    }

    @Test
    public void isNotUnique() {
        assertFalse(UniqueString.isUnique("abcad"));
        assertFalse(UniqueString.isUnique("aa"));
        assertFalse(UniqueString.isUnique("abccd"));
    }

}