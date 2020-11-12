package java8.time_api.clock;

import java.time.Instant;

class TimeElapse {
    private final Instant endTime = Clocks.instance.getClock().instant().plusSeconds(60*60);

    boolean isHourElapsed() {
        return  Clocks.instance.getClock().instant().isAfter(endTime);
    }
}
