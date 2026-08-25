package ru.yaal.examples.android.lifecycle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.test.annotation.UiThreadTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4ClassRunner.class)
public class LiveDataTest {

    @Test
    @UiThreadTest
    public void observeLiveData() {
        MutableLiveData<String> liveData = new MutableLiveData<>();

        StringObserver observer = new StringObserver();
        liveData.observeForever(observer);

        assertNull(observer.value);

        String newValue = "abc";
        liveData.setValue(newValue);

        assertThat(observer.value, equalTo(newValue));
    }

    private static class StringObserver implements Observer<String> {
        String value;

        @Override
        public void onChanged(String s) {
            value = s;
        }
    }
}
