package util.date;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

class DateFormatTest {
    @Test
    void simpleDateFormat() {
        var format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        var date = Date.from(Instant.parse("2007-12-03T10:15:30.00Z"));
        var str = format.format(date);
        assertThat(str).isEqualTo("2007-12-03 18:15:30");
    }

    @Test
    void simpleDateFormatWithTimeZone() {
        var format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        format.setTimeZone(TimeZone.getTimeZone("PST"));
        var date = Date.from(Instant.parse("2007-12-03T10:15:30.00Z"));
        var str = format.format(date);
        assertThat(str).isEqualTo("2007-12-03 02:15:30 PST");
    }
}
