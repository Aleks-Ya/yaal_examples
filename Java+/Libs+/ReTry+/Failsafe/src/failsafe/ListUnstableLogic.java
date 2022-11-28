package failsafe;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class ListUnstableLogic<T> implements Callable<T> {
    private final List<? extends Exception> exceptions;
    private final T result;
    private final AtomicInteger counter = new AtomicInteger();

    public ListUnstableLogic(List<? extends Exception> exceptions, T result) {
        this.result = result;
        this.exceptions = exceptions;
    }

    @Override
    public T call() throws Exception {
        var attempt = counter.getAndAdd(1);
        if (attempt < exceptions.size()) {
            throw exceptions.get(attempt);
        }
        return result;
    }
}
