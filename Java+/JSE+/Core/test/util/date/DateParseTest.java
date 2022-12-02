package util.date;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;

class DateParseTest {
    @Test
    void parseWithSimpleDateFormat() throws ParseException {
        var format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        var date = format.parse("2016-12-20 11:34:15");
        assertThat(date).hasToString("Tue Dec 20 11:34:15 PST 2016");
    }
}
