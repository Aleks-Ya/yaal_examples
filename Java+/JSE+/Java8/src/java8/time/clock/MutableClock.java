package java8.time.clock;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;

class MutableClock extends Clock {
    private final ZoneId zoneId;
    private Instant instant;

    public MutableClock(ZoneId zoneId, Instant instant) {
        this.zoneId = zoneId;
        this.instant = instant;
    }

    public static MutableClock mutable() {
        return new MutableClock(ZoneId.systemDefault(), Instant.now());
    }

    public static MutableClock mutable(Instant instant) {
        return new MutableClock(ZoneId.systemDefault(), instant);
    }

    @Override
    public ZoneId getZone() {
        return zoneId;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return new MutableClock(zone, instant);
    }

    @Override
    public Instant instant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public void plus(long amountToAdd, TemporalUnit unit) {
        instant = instant.plus(amountToAdd, unit);
    }

    public void plus(Duration duration) {
        instant = instant.plus(duration);
    }
}
