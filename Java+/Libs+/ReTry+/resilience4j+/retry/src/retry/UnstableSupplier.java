package retry;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class UnstableSupplier<T> implements Supplier<T> {
    private final AtomicInteger counter = new AtomicInteger();
    private final T result;
    private final int failedRepetitions;

    public UnstableSupplier(T result, int failedRepetitions) {
        this.result = result;
        this.failedRepetitions = failedRepetitions;
    }

    @Override
    public T get() {
        var attempt = counter.getAndAdd(1);
        if (attempt < failedRepetitions) {
            throw new RuntimeException("abc");
        }
        return result;
    }
}
