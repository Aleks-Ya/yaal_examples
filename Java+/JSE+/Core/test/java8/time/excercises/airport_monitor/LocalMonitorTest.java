package java8.time.excercises.airport_monitor;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class LocalMonitorTest {
    private final CentralMonitor centralMonitor = new CentralMonitor();
    private final Airport belmontAirport = new Airport("Belmont", ZoneId.of("-7"));
    private final Airport astanaAirport = new Airport("Astana", ZoneId.of("+6"));
    private final LocalMonitor astanaMonitor = new LocalMonitor(centralMonitor, astanaAirport);
    private final LocalMonitor belmontMonitor = new LocalMonitor(centralMonitor, belmontAirport);

    @Test
    void printFlights() {
        var departure = new Event(belmontAirport, Instant.parse("2007-03-25T10:15:00.00Z"));
        var arrival = new Event(astanaAirport, Instant.parse("2007-03-27T03:45:00.00Z"));

        var flight = new Flight(departure, arrival);

        centralMonitor.addFlight(flight);

        var belmontPrint = belmontMonitor.printFlights();
        var astanaPrint = astanaMonitor.printFlights();

        System.out.println(belmontPrint);
        System.out.println(astanaPrint);

        assertThat(belmontPrint).isEqualTo("On Belmont monitor (-07:00):\n" +
                "Belmont 25.03.2007 03:15 - Astana 26.03.2007 20:45\n");
        assertThat(astanaPrint).isEqualTo("On Astana monitor (+06:00):\n" +
                "Belmont 25.03.2007 16:15 - Astana 27.03.2007 09:45\n");
    }
}