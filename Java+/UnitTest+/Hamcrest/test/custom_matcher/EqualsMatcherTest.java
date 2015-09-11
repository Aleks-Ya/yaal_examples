package custom_matcher;

import org.junit.Test;

import static org.junit.Assert.assertThat;

/**
 * Самописный матечер для Hamcrest.
 */
public class EqualsMatcherTest {
    @Test
    public void pass() {
        int a = 1;
        assertThat(a, EqualsMatcher.customEqualsTo(a));
    }

    @Test(expected = AssertionError.class)
    public void fail() {
        assertThat(1, EqualsMatcher.customEqualsTo(2));
    }
}
