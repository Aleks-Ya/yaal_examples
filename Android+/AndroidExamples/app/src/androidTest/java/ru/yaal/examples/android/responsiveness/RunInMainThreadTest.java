package ru.yaal.examples.android.responsiveness;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.test.annotation.UiThreadTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4ClassRunner.class)
public class RunInMainThreadTest {

    @Test
    @UiThreadTest
    public void byUiThreadTest() {
        assertTrue(ArchTaskExecutor.getInstance().isMainThread());
    }


    @Test
    public void byArchTaskExecutor() {
        assertFalse(ArchTaskExecutor.getInstance().isMainThread());
        ArchTaskExecutor.getInstance().postToMainThread(new Runnable() {
            public void run() {
                Log.i("UI thread", "I am the UI thread");
                assertTrue(ArchTaskExecutor.getInstance().isMainThread());
            }
        });
    }

    @Test
    public void byContext() {
        assertFalse(ArchTaskExecutor.getInstance().isMainThread());
        Context context = InstrumentationRegistry.getInstrumentation().getContext();

        context.getMainExecutor().execute(new Runnable() {
            public void run() {
                Log.i("UI thread", "I am the UI thread 2");
                assertTrue(ArchTaskExecutor.getInstance().isMainThread());
            }
        });
    }
}
