import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

public class Manipulation {

    @Test
    void decrement() {
        DateTime date = new DateTime().minusMinutes(1);
    }
}
