import org.joda.time.DateTime;

public class ParsingString {
    public static void main(String[] args) {
        DateTime yyyyMmDd = DateTime.parse("2013-08-15"); // 2013-08-15T00:00:00.000+04:00
        DateTime yyyyMmDdHhMm = DateTime.parse("2013-08-15T10:32"); // 2013-08-15T10:32:00.000+04:00
        DateTime yyyyMmDdHhMmSs = DateTime.parse("2013-08-15T10:32:16"); // 2013-08-15T10:32:16.000+04:00
        DateTime yyyyMmDdHhMmSs2 = new DateTime("2013-08-15T10:32:16"); // 2013-08-15T10:32:16.000+04:00
        print(yyyyMmDd, yyyyMmDdHhMm, yyyyMmDdHhMmSs, yyyyMmDdHhMmSs2);
    }

    private static void print(DateTime... times) {
        for (DateTime time : times) {
            System.out.println(time);
        }
    }

}