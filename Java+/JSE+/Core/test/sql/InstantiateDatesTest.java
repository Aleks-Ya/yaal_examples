package sql;

import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Create instances of java.sql.Date, java.sql.Time and java.sql.Timestamp.
 */
public class InstantiateDatesTest {

    @Test
    public void parseString() {
        Date date = Date.valueOf("2017-03-14");
        System.out.println("Date: " + date);

        Time time = Time.valueOf("19:50:45");
        System.out.println("Time: " + time);

        Timestamp timestamp = Timestamp.valueOf("2017-12-25 06:18:25");
        System.out.println("Timestamp: " + timestamp);
    }

    @Test
    public void fromLocal() {
        Date date = Date.valueOf(LocalDate.parse("2017-01-30"));
        System.out.println("Date: " + date);

        Time time = Time.valueOf(LocalTime.parse("20:50:45"));
        System.out.println("Time: " + time);

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.parse("2016-12-25T23:40:30"));
        System.out.println("Timestamp: " + timestamp);
    }

    @Test
    public void fromLocal2() {
        Date date = Date.valueOf(LocalDate.parse("2017-01-30"));
        System.out.println("Date: " + date);

        Time time = Time.valueOf(LocalTime.parse("20:50:45"));
        System.out.println("Time: " + time);

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.parse("2016-12-25T23:40:30"));
        System.out.println("Timestamp: " + timestamp);
    }

    @Test
    public void fromPythonUtcNow() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.parse("2016-12-25T23:40:30.234778"));
        System.out.println("Timestamp: " + timestamp);
    }
}