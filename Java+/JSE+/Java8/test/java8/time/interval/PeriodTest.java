package java8.time.interval;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

class PeriodTest {
    @Test
    void instantiate() {
        var period = Period.between(LocalDate.now(), LocalDate.now().plusDays(1));
    }

}
