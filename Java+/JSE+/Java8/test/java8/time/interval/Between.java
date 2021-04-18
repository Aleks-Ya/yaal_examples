package java8.time.interval;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Интервал между датами.
 */
public class Between {
    @Test
    public void date() {
        var date1 = LocalDate.of(2017, 10, 1);
        long daysToAdd = 3;
        var date2 = date1.plusDays(daysToAdd);

        var between = ChronoUnit.DAYS.between(date1, date2);
        assertThat(between, equalTo(daysToAdd));
    }
}
