package time_api;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Преобразование одних классов даты/времени в другие.
 *
 * @author yablokov a.
 */
public class Convert {

    @Test
    public void localDateToLocalDateTime() {
        LocalDate date = LocalDate.parse("2015-03-25");
        LocalTime time = LocalTime.MIDNIGHT;

        LocalDateTime dateTime = LocalDateTime.of(date, time);

        assertThat(dateTime.toString(), equalTo("2015-03-25T00:00"));
    }
}
