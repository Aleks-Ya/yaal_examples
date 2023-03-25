package failsafe;

import java.time.Duration;
import java.util.concurrent.Callable;

public class LongTimeCallable<T> implements Callable<T> {
    private final T result;
    private final Duration duration;

    public LongTimeCallable(T result, Duration duration) {
        this.result = result;
        this.duration = duration;
    }

    @Override
    public T call() throws InterruptedException {
        Thread.sleep(duration.toMillis());
        return result;
    }
}
