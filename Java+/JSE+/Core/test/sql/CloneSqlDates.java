package sql;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class CloneSqlDates {

    @Test
    void cloneSqlTimestamp() {
        var origin = Timestamp.valueOf("2017-12-25 06:18:25.123456");
        var clone1 = Timestamp.from(origin.toInstant());
        var clone2 = Timestamp.valueOf(origin.toString());

        assertThat(clone1, equalTo(origin));
        assertThat(clone2, equalTo(origin));
    }
}