package java8.time.clock;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

class TimeService {
    private final Clock clock;
    private final Instant creationInstant;

    public TimeService(Clock clock) {
        this.clock = clock;
        creationInstant = clock.instant();
    }

    public Duration timePastFromCreation() {
        return Duration.between(creationInstant, clock.instant());
    }
}
