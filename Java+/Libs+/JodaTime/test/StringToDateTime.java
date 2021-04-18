import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;


public class StringToDateTime {

    @Test
    public void useMethodParse() {
        DateTime yyyyMmDd = DateTime.parse("2013-08-15"); // 2013-08-15T00:00:00.000+04:00
        DateTime yyyyMmDdHhMm = DateTime.parse("2013-08-15T10:32"); // 2013-08-15T10:32:00.000+04:00
        DateTime yyyyMmDdHhMmSs = DateTime.parse("2013-08-15T10:32:16"); // 2013-08-15T10:32:16.000+04:00

        print(yyyyMmDd, yyyyMmDdHhMm, yyyyMmDdHhMmSs);
    }

    @Test
    public void useConstructor() {
        DateTime yyyyMmDdHhMmSs2 = new DateTime("2013-08-15T10:32:16"); // 2013-08-15T10:32:16.000+04:00
        print(yyyyMmDdHhMmSs2);
    }

    @Test
    public void useFormatter() {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm");
        DateTime HhMm = DateTime.parse("10:32", fmt); // 10:32:16.000+04:00

        print(HhMm);
    }

    private void print(DateTime... times) {
        for (DateTime time : times) {
            System.out.println(time);
        }
    }
}