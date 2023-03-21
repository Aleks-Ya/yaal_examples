package retry;

import io.github.resilience4j.core.functions.CheckedSupplier;

import java.net.ConnectException;
import java.util.concurrent.atomic.AtomicInteger;

public class UnstableCheckedSupplier<T> implements CheckedSupplier<T> {
    private final AtomicInteger counter = new AtomicInteger();
    private final T result;
    private final int failedRepetitions;

    public UnstableCheckedSupplier(T result, int failedRepetitions) {
        this.result = result;
        this.failedRepetitions = failedRepetitions;
    }

    @Override
    public T get() throws ConnectException {
        var attempt = counter.getAndAdd(1);
        if (attempt < failedRepetitions) {
            throw new ConnectException("abc");
        }
        return result;
    }
}
