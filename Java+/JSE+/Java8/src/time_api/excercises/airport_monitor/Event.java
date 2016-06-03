package time_api.excercises.airport_monitor;

import java.time.Instant;

class Event {
    private final Airport airport;
    private final Instant instant;

    Event(Airport airport, Instant instant) {
        this.airport = airport;
        this.instant = instant;
    }

    Airport getAirport() {
        return airport;
    }

    Instant getInstant() {
        return instant;
    }
}
