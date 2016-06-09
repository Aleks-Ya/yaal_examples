import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Создаем объект с заданными датой и временем.
 */
public class Initialize {

    @Test
    public void init() {
        new DateTime(2015, 3, 25, 15, 45, 20);
    }
}
