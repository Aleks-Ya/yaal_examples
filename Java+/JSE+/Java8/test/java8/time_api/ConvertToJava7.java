package java8.time_api;

import org.junit.Test;

import java.time.ZoneId;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Преобразование классов времени Java8 в Java7 и наоборот.
 */
public class ConvertToJava7 {

    @Test
    public void zoneIdToTimeZone() {
        ZoneId zoneId = ZoneId.systemDefault();
        TimeZone timeZone = TimeZone.getTimeZone(zoneId.getId());
        assertThat(timeZone.getID(), equalTo(zoneId.getId()));
    }
}
