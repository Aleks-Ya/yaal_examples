package custom_matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

class DescribeMismatchMatcher extends BaseMatcher<Object> {
    @Override
    public void describeTo(Description description) {
        description.appendText("Describe expected object");
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        description.appendText("Describe actual object");
    }

    @Override
    public boolean matches(Object item) {
        return false;
    }

    @Factory
    public static Matcher<Object> customEqualsTo(Object other) {
        return new DescribeMismatchMatcher();
    }

}
