package retry;

import java.util.concurrent.atomic.AtomicInteger;

public class UnstableRunnable implements Runnable {
    private final AtomicInteger counter = new AtomicInteger();
    private final int failedRepetitions;

    public UnstableRunnable(int failedRepetitions) {
        this.failedRepetitions = failedRepetitions;
    }

    @Override
    public void run() {
        var attempt = counter.getAndAdd(1);
        if (attempt < failedRepetitions) {
            throw new RuntimeException("abc");
        }
    }

    public AtomicInteger getCounter() {
        return counter;
    }
}
