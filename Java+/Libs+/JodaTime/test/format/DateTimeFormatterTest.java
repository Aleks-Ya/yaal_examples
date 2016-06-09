package format;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Yablokov Aleksey
 */
public class DateTimeFormatterTest {
    private static final DateTime dateTime = new DateTime(2015, 3, 25, 15, 45, 20, DateTimeZone.forID("+02:00"));
    private static final DateTimeFormatter defFormatter = DateTimeFormat.forPattern("kk:mm:ss dd.MM.yyyy");

    @Test
    public void forPattern() {
        assertThat(defFormatter.print(dateTime), equalTo("15:45:20 25.03.2015"));
    }

    @Test
    public void withZone() {
        DateTimeFormatter formatter = defFormatter.withZone(DateTimeZone.forID("+01:00"));
        assertThat(formatter.print(dateTime), equalTo("16:45:20 25.03.2015"));
    }

    @Test
    public void withZoneFromDateTime() {
        DateTimeFormatter formatter = defFormatter.withZone(dateTime.getZone());
        assertThat(formatter.print(dateTime), equalTo("16:45:20 25.03.2015"));
    }

    @Test
    public void now() {
        DateTime now = new DateTime();
        System.out.println(defFormatter.print(now));
    }

    @Test
    public void javaUtilDate() {
        Date date = new Date(1465301649214L);
        assertThat(defFormatter.print(date.getTime()), equalTo("15:14:09 07.06.2016"));
    }
}
