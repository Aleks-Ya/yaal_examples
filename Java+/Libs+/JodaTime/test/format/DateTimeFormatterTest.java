package format;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Yablokov Aleksey
 */
public class DateTimeFormatterTest {
    private static final DateTime dateTime = new DateTime(2015, 3, 25, 15, 45, 20, DateTimeZone.forID("+02:00"));

    @Test
    public void forPattern() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("kk:mm:ss dd.MM.yyyy");
        assertThat(formatter.print(dateTime), equalTo("15:45:20 25.03.2015"));
    }

    @Test
    public void withZone() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("kk:mm:ss dd.MM.yyyy").withZone(DateTimeZone.forID("+01:00"));
        assertThat(formatter.print(dateTime), equalTo("16:45:20 25.03.2015"));
    }
}
