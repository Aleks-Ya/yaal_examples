package time_api.excercises.airport_monitor;

import java.time.format.DateTimeFormatter;

class LocalMonitor {
    private final CentralMonitor monitor;
    private final Airport airport;

    LocalMonitor(CentralMonitor monitor, Airport airport) {
        this.monitor = monitor;
        this.airport = airport;
    }

    String printFlights() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm").withZone(airport.getZoneId());
        StringBuilder sb = new StringBuilder("On ").append(airport.getName()).append(" monitor (").append(airport.getZoneId()).append("):\n");
        for (Flight flight : monitor.getFlights()) {
            Event departure = flight.getDeparture();
            Event arrival = flight.getArrival();

            sb.append(departure.getAirport().getName()).append(" ");
            sb.append(formatter.format(departure.getInstant())).append(" - ");

            sb.append(arrival.getAirport().getName()).append(" ");
            sb.append(formatter.format(arrival.getInstant()));
        }
        sb.append("\n");
        return sb.toString();
    }
}
