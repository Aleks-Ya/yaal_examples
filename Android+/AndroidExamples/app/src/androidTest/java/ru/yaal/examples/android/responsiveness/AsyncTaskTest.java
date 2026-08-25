package ru.yaal.examples.android.responsiveness;

import android.os.AsyncTask;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

@RunWith(AndroidJUnit4ClassRunner.class)
public class AsyncTaskTest {

    @Test
    public void doInWorkerThread() throws ExecutionException, InterruptedException {
        System.out.println("UI thread: " + Thread.currentThread().getName());
        AsyncTask<Integer, Integer, Long> asyncTask = new DownloadFilesTask().execute(3, 5, 4);
        System.out.println("Result: " + asyncTask.get());
    }

    private class DownloadFilesTask extends AsyncTask<Integer, Integer, Long> {

        // Do the long-running work in here
        @Override
        protected Long doInBackground(Integer... intervals) {
            System.out.println("Current thread: " + Thread.currentThread().getName());
            for (int i = 0; i < intervals.length; i++) {
                int interval = intervals[i];
                try {
                    int progress = i * 100 / intervals.length;
                    publishProgress(progress);
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return 42L;
        }

        // This is called each time you call publishProgress()
        @Override
        protected void onProgressUpdate(Integer... progress) {
            System.out.println("Progresses: " + Arrays.deepToString(progress));
        }

        // This is called when doInBackground() is finished
        @Override
        protected void onPostExecute(Long result) {
            System.out.println("Finished");
        }
    }

}
