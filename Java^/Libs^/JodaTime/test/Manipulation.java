import org.joda.time.DateTime;
import org.junit.Test;

public class Manipulation {

    @Test
    public void decrement() {
        DateTime date = new DateTime().minusMinutes(1);
    }
}
