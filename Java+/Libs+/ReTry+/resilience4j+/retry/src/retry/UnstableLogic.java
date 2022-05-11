package retry;

import java.net.ConnectException;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class UnstableLogic<T> implements Callable<T> {
    private final AtomicInteger counter = new AtomicInteger();
    private final T result;
    private final int failedRepetitions;

    public UnstableLogic(T result, int failedRepetitions) {
        this.result = result;
        this.failedRepetitions = failedRepetitions;
    }

    @Override
    public T call() throws ConnectException {
        var attempt = counter.getAndAdd(1);
        if (attempt < failedRepetitions) {
            throw new ConnectException("abc");
        }
        return result;
    }
}
