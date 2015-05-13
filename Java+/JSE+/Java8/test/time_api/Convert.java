package time_api;

import org.junit.Test;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * Преобразование одних классов даты/времени в другие.
 *
 * @author yablokov a.
 */
public class Convert {

    /**
     * Конвертировать из LocalDate в LocalDateTime невозможно,
     * т.к. в LocalDate нет информации о времени.
     */
    @Test(expected = DateTimeException.class)
    public void localDateToLocalDateTime() {
        LocalDate date = LocalDate.parse("2015-03-25");
        LocalDateTime dateTime = LocalDateTime.from(date);
    }
}
