package time_api.excercises.airport_monitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class CentralMonitor {
    private final List<Flight> flights = new ArrayList<>();

    void addFlight(Flight flight) {
        flights.add(flight);
    }

    List<Flight> getFlights() {
        return Collections.unmodifiableList(flights);
    }

}
