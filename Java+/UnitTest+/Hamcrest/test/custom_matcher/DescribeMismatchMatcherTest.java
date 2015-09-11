package custom_matcher;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertThat;

/**
 * Описание, почему объекты на равны.
 */
public class DescribeMismatchMatcherTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void test() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
                "Assert message\n" +
                        "Expected: Describe expected object\n" +
                        "     but: Describe actual object");
        assertThat("Assert message", 1, DescribeMismatchMatcher.customEqualsTo(2));
    }
}