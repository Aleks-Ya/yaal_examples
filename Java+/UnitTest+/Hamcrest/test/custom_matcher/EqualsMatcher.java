package custom_matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class EqualsMatcher extends BaseMatcher<Object> {
    private final Object other;

    public EqualsMatcher(Object other) {
        this.other = other;
    }

    @Override
    public boolean matches(Object item) {
        return item.equals(other);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Not equals");
    }

    @Factory
    public static Matcher<Object> customEqualsTo(Object other) {
        return new EqualsMatcher(other);
    }

}
