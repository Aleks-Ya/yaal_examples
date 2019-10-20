package ru.yaal.examples.android;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule =
            new ActivityTestRule<>(MainActivity.class, true, true);


    @Test
    public void containsButtons() {
        onView(withId(R.id.recyclerViewActivityButton))
                .check(matches(isDisplayed()))
                .check(matches(withSubstring("Recycler View")));

        onView(withId(R.id.viewModelFragmentActivityButton2))
                .check(matches(isDisplayed()))
                .check(matches(withSubstring("View Model Fragment")));

        onView(withId(R.id.fragmentActivityButton2))
                .check(matches(isDisplayed()))
                .check(matches(withSubstring("Plain Fragment")));

        onView(withId(R.id.recyclerViewFragmentActivityButton))
                .check(matches(isDisplayed()))
                .check(matches(withSubstring("Recycler View Custom View")));

        onView(withId(R.id.progressBarActivityButton))
                .check(matches(isDisplayed()))
                .check(matches(withSubstring("Progress bar activity")));
    }
}