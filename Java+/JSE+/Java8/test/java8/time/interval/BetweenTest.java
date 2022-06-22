package java8.time.interval;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Интервал между датами.
 */
class BetweenTest {
    @Test
    void date() {
        var date1 = LocalDate.of(2017, 10, 1);
        long daysToAdd = 3;
        var date2 = date1.plusDays(daysToAdd);

        var between = ChronoUnit.DAYS.between(date1, date2);
        assertThat(between).isEqualTo(daysToAdd);
    }
}
