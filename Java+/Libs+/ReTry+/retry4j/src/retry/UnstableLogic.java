package retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class UnstableLogic<T> implements Callable<T> {
    private static final Logger log = LoggerFactory.getLogger(UnstableLogic.class);
    private final AtomicInteger counter = new AtomicInteger();
    private final T result;
    private final int failedRepetitions;

    public UnstableLogic(T result, int failedRepetitions) {
        this.result = result;
        this.failedRepetitions = failedRepetitions;
    }

    @Override
    public T call() throws ConnectException {
        log.info("Trying to execute...");
        var attempt = counter.getAndAdd(1);
        if (attempt < failedRepetitions) {
            log.info("Execution failed.");
            throw new ConnectException("abc");
        }
        log.info("Execution succeeded.");
        return result;
    }
}
