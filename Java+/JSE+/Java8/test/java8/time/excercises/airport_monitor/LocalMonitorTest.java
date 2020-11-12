package java8.time.excercises.airport_monitor;

import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LocalMonitorTest {
    private CentralMonitor centralMonitor = new CentralMonitor();
    private Airport belmontAirport = new Airport("Belmont", ZoneId.of("-7"));
    private Airport astanaAirport = new Airport("Astana", ZoneId.of("+6"));
    private LocalMonitor astanaMonitor = new LocalMonitor(centralMonitor, astanaAirport);
    private LocalMonitor belmontMonitor = new LocalMonitor(centralMonitor, belmontAirport);

    @Test
    public void printFlights() {
        Event departure = new Event(belmontAirport, Instant.parse("2007-03-25T10:15:00.00Z"));
        Event arrival = new Event(astanaAirport, Instant.parse("2007-03-27T03:45:00.00Z"));

        Flight flight = new Flight(departure, arrival);

        centralMonitor.addFlight(flight);

        String belmontPrint = belmontMonitor.printFlights();
        String astanaPrint = astanaMonitor.printFlights();

        System.out.println(belmontPrint);
        System.out.println(astanaPrint);

        assertThat(belmontPrint, equalTo("On Belmont monitor (-07:00):\n" +
                "Belmont 25.03.2007 03:15 - Astana 26.03.2007 20:45\n"));
        assertThat(astanaPrint, equalTo("On Astana monitor (+06:00):\n" +
                "Belmont 25.03.2007 16:15 - Astana 27.03.2007 09:45\n"));
    }
}