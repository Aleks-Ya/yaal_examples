package ru.yaal.examples.android.resource;

import android.content.Context;
import android.content.res.Resources;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import ru.yaal.examples.android.R;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4ClassRunner.class)
public class ReadResourcesTest {

    @Test
    public void stringResource() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Resources resources = context.getResources();

        int resourceId = R.string.app_name;

        String resourceName = resources.getResourceName(resourceId);
        assertThat(resourceName, equalTo("ru.yaal.examples.android:string/app_name"));

        String resourceValue = resources.getString(resourceId);
        assertThat(resourceValue, equalTo("AndroidExamples"));
    }
}