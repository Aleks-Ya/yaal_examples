package util.date;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateParseTest {
    @Test
    public void parseWithSimpleDateFormat() throws ParseException {
        var format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        var str = format.parse("2016-12-20 11:34:15");
        assertEquals("Tue Dec 20 11:34:15 MSK 2016", str.toString());
    }
}
