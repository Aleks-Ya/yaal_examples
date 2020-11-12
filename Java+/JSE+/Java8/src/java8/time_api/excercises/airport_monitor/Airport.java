package java8.time_api.excercises.airport_monitor;

import java.time.ZoneId;

class Airport {
    private final String name;
    private final ZoneId zoneId;

    Airport(String name, ZoneId zoneId) {
        this.name = name;
        this.zoneId = zoneId;
    }

    String getName() {
        return name;
    }

    ZoneId getZoneId() {
        return zoneId;
    }
}
