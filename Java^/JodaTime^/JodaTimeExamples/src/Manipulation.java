import org.joda.time.DateTime;

public class Manipulation {
    public static void main(String[] args) {
        decrement();
    }

    private static void decrement() {
        DateTime fiscalizeDate = new DateTime().minusMinutes(1);
    }
}
