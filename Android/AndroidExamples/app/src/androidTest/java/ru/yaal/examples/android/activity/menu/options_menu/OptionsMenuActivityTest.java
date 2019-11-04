package ru.yaal.examples.android.activity.menu.options_menu;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.yaal.examples.android.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class OptionsMenuActivityTest {

    @Rule
    public ActivityTestRule<OptionsMenuActivity> rule =
            new ActivityTestRule<>(OptionsMenuActivity.class, true, true);


    @Test
    public void containsOpenMenuText() {
        onView(withId(R.id.optionsMenuTextView))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.options_menu_text_view_init_text)));
    }

    @Test
    public void clickHiMenuItem() {
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.options_menu_hi_item_text))
                .perform(click());
        onView(withId(R.id.optionsMenuTextView))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.options_menu_text_view_hi_text)));
    }

    @Test
    public void clickByeMenuItem() {
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.options_menu_bye_item_text))
                .perform(click());
        onView(withId(R.id.optionsMenuTextView))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.options_menu_text_view_bye_text)));
    }
}