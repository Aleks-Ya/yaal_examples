package ru.yaal.examples.android.activity.progress.progressbar;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.yaal.examples.android.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class ProgressBarActivityTest {

    @Rule
    public ActivityTestRule<ProgressBarActivity> progressBarActivityRule =
            new ActivityTestRule<>(ProgressBarActivity.class, true, true);


    @Test
    public void containsProgressBar() {
        onView(withId(R.id.progressBar))
                .check(matches(isDisplayed()));
    }
}