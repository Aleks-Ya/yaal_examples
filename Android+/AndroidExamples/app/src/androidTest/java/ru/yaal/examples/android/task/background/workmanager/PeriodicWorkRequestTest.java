package ru.yaal.examples.android.task.background.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.work.Configuration;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import androidx.work.testing.SynchronousExecutor;
import androidx.work.testing.WorkManagerTestInitHelper;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4ClassRunner.class)
public class PeriodicWorkRequestTest {

    @Test
    public void runWork() throws ExecutionException, InterruptedException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Configuration config = new Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .setExecutor(new SynchronousExecutor())
                .build();

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config);

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(
                UploadWorker.class, 2, TimeUnit.SECONDS)
                .build();
        Operation operation = WorkManager.getInstance(context).enqueue(request);
        Thread.sleep(10000);
        Operation.State state = operation.getResult().get();
        assertThat(state, Matchers.instanceOf(Operation.State.SUCCESS.class));
    }

    public static class UploadWorker extends Worker {

        public UploadWorker(@NonNull Context context, @NonNull WorkerParameters params) {
            super(context, params);
        }

        @NonNull
        @Override
        public Result doWork() {
            Log.e(PeriodicWorkRequestTest.class.getName(), "Uploading periodically...");
            return Result.success();
        }
    }

}

