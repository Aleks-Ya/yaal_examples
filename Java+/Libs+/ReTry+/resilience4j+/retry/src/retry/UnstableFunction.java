package retry;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class UnstableFunction implements Function<Integer, String> {
    private final AtomicInteger counter = new AtomicInteger();
    private final int failedRepetitions;

    public UnstableFunction(int failedRepetitions) {
        this.failedRepetitions = failedRepetitions;
    }

    @Override
    public String apply(Integer t) {
        var attempt = counter.getAndAdd(1);
        if (attempt < failedRepetitions) {
            throw new RuntimeException("abc");
        }
        return t.toString();
    }
}
