package sql;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Create instances of java.sql.Date, java.sql.Time and java.sql.Timestamp.
 */
class InstantiateSqlDates {

    @Test
    void parseString() {
        var date = Date.valueOf("2017-03-14");
        System.out.println("Date: " + date);

        var time = Time.valueOf("19:50:45");
        System.out.println("Time: " + time);

        var timestamp = Timestamp.valueOf("2017-12-25 06:18:25");
        System.out.println("Timestamp: " + timestamp);
    }

    @Test
    void parseString2() {
        var ts1 = Timestamp.valueOf("2019-02-12 06:24:05.872662");
        System.out.println("TS: " + ts1);
        var ts2 = new Timestamp(ts1.getTime());
        System.out.println("TS: " + ts2);
    }


    @Test
    void fromLocal() {
        var date = Date.valueOf(LocalDate.parse("2017-01-30"));
        System.out.println("Date: " + date);

        var time = Time.valueOf(LocalTime.parse("20:50:45"));
        System.out.println("Time: " + time);

        var timestamp = Timestamp.valueOf(LocalDateTime.parse("2016-12-25T23:40:30"));
        System.out.println("Timestamp: " + timestamp);
    }

    @Test
    void fromLocal2() {
        var date = Date.valueOf(LocalDate.parse("2017-01-30"));
        System.out.println("Date: " + date);

        var time = Time.valueOf(LocalTime.parse("20:50:45"));
        System.out.println("Time: " + time);

        var timestamp = Timestamp.valueOf(LocalDateTime.parse("2016-12-25T23:40:30"));
        System.out.println("Timestamp: " + timestamp);
    }

    @Test
    void fromPythonUtcNow() {
        var timestamp = Timestamp.valueOf(LocalDateTime.parse("2016-12-25T23:40:30.234778"));
        System.out.println("Timestamp: " + timestamp);
    }
}