package format;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Yablokov Aleksey
 */
class DateTimeFormatterBuilderTest {
    @Test
    void dateTimeFormat() {
        final var dateTime = new DateTime(2015, 3, 25, 15, 45, 20, DateTimeZone.forID("+02:00"));

        var builder = new DateTimeFormatterBuilder();

        var formatter = DateTimeFormat.forPattern("kk:mm:ss dd.MM.yyyy");
        assertThat(formatter.print(dateTime)).isEqualTo("15:45:20 25.03.2015");

        var formatterWithTimeZone = formatter.withZone(DateTimeZone.forID("+01:00"));
        assertThat(formatterWithTimeZone.print(dateTime)).isEqualTo("16:45:20 25.03.2015");
    }
}
