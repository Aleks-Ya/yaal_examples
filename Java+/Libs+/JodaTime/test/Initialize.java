import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

/**
 * Создаем объект с заданными датой и временем.
 */
public class Initialize {

    @Test
    void init() {
        new DateTime(2015, 3, 25, 15, 45, 20);
    }
}
