package time_api.clock;

import java.time.Clock;

class Clocks {
    static final Clocks instance = new Clocks();
    private volatile Clock clock;

    private Clocks() {
    }

    synchronized void setClock(Clock clock) {
        this.clock = clock;
    }

    synchronized Clock getClock() {
        return clock;
    }
}
