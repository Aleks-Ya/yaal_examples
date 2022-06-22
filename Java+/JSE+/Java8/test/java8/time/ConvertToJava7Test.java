package java8.time;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Преобразование классов времени Java8 в Java7 и наоборот.
 */
class ConvertToJava7Test {

    @Test
    void zoneIdToTimeZone() {
        var zoneId = ZoneId.systemDefault();
        var timeZone = TimeZone.getTimeZone(zoneId.getId());
        assertThat(timeZone.getID()).isEqualTo(zoneId.getId());
    }
}
