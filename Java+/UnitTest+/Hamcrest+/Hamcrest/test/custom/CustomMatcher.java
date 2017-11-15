package custom;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class CustomMatcher {
    @Test
    public void isEven() {
        assertThat(1, not(IsEven.isEven()));
        assertThat(2, IsEven.isEven());
    }


    private static class IsEven extends TypeSafeDiagnosingMatcher<Integer> {

        @SuppressWarnings("WeakerAccess")
        public static IsEven isEven() {
            return new IsEven();
        }

        @Override
        protected boolean matchesSafely(Integer integer, Description description) {
            description.appendText("was ").appendValue(integer).appendText(", which is an Odd number");
            return integer % 2 == 0;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("An Even number");
        }

    }
}