package format;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Yablokov Aleksey
 */
public class DateTimeFormatterBuilderTest {
    @Test
    public void dateTimeFormat() {
        final DateTime dateTime = new DateTime(2015, 3, 25, 15, 45, 20, DateTimeZone.forID("+02:00"));

        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("kk:mm:ss dd.MM.yyyy");
        assertThat(formatter.print(dateTime), equalTo("15:45:20 25.03.2015"));

        DateTimeFormatter formatterWithTimeZone = formatter.withZone(DateTimeZone.forID("+01:00"));
        assertThat(formatterWithTimeZone.print(dateTime), equalTo("16:45:20 25.03.2015"));
    }
}
