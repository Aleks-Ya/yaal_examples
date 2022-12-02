package util.date;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Different dates are equal.
 */
class WrongDateComparingTest {
    @Test
    void defaultLocal() {
        var d1 = Date.valueOf("1582-10-10");
        var d2 = Date.valueOf("1582-10-20");
        assertThat(d2).isEqualTo(d1);
    }
}