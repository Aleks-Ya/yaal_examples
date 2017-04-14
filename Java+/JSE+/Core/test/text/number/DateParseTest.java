package text.number;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateParseTest {
    @Test
    public void integer() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date str = format.parse("2016-12-20 11:34:15");
        assertEquals("Tue Dec 20 11:34:15 MSK 2016", str.toString());
    }
}
