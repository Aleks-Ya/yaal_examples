package java8.time_api.interval;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Period;

public class PeriodTest {
    @Test
    public void instantiate() {
        Period period = Period.between(LocalDate.now(), LocalDate.now().plusDays(1));
    }

}
