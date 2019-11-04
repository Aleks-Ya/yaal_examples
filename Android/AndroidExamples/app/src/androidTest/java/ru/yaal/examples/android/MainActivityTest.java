package ru.yaal.examples.android;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void containsRecyclerViewActivityButton() {
        onView(withId(R.id.recyclerViewActivityButton))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.to_recycler_view_button)))
                .perform(click())
                .check(doesNotExist());
        onView(withId(R.id.my_recycler_view))
                .check(matches(hasChildCount(3)))
                .check(matches(allOf(
                        withChild(withText("abc")),
                        withChild(withText("123")),
                        withChild(withText("efg")))
                ))
                .check(matches(isDisplayed()));
        onView(withSubstring("abc")).check(matches(isDisplayed()));
        onView(withSubstring("123")).check(matches(isDisplayed()));
        onView(withSubstring("efg")).check(matches(isDisplayed()));
    }

    @Test
    public void containsViewModelFragmentActivityButton() {
        onView(withId(R.id.viewModelFragmentActivityButton2))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.to_view_model_fragment_button)));
    }

    @Test
    public void containsFragmentActivityButton() {
        onView(withId(R.id.fragmentActivityButton2))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.to_plain_fragment_button)));
    }

    @Test
    public void containsRecyclerViewFragmentActivityButton() {
        onView(withId(R.id.recyclerViewFragmentActivityButton))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.to_recycler_view_custom_view_button)));
    }

    @Test
    public void containsProgressBarButton() {
        onView(withId(R.id.progressBarActivityButton))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.to_progress_bar_button)))
                .perform(click());

        onView(withId(R.id.progressBar))
                .check(matches(isDisplayed()));
    }

    @Test
    public void containsOptionsMenuButton() {
        onView(withId(R.id.optionsMenuActivityButton))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.to_options_menu_button)))
                .perform(click());

        onView(withId(R.id.optionsMenuTextView))
                .check(matches(isDisplayed()));
    }

}