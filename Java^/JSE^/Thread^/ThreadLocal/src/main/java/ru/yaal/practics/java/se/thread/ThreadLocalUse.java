package ru.yaal.practics.java.se.thread;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ThreadLocalUse {
    private static Runnable work = new Runnable() {
        private ThreadLocal<Date> date = new ThreadLocal<Date>() {
            @Override
            protected Date initialValue() {
                return new Date();
            }
        };
        private DateFormat df = DateFormat.getTimeInstance();

        @Override
        public void run() {
            System.out.printf("Thread name=%s, date=%s\n", Thread.currentThread().getName(), df.format(date.get()));
        }
    };

    public static void main(String[] args) throws InterruptedException {
        ExecutorService se = Executors.newFixedThreadPool(2);
        se.submit(work);
        TimeUnit.SECONDS.sleep(2);
        se.submit(work);
        se.shutdown();
    }
}