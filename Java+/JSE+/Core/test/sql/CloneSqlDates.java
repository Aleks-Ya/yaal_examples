package sql;

import org.junit.Test;

import java.sql.Timestamp;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CloneSqlDates {

    @Test
    public void cloneSqlTimestamp() {
        Timestamp origin = Timestamp.valueOf("2017-12-25 06:18:25.123456");
        Timestamp clone1 = Timestamp.from(origin.toInstant());
        Timestamp clone2 = Timestamp.valueOf(origin.toString());

        assertThat(clone1, equalTo(origin));
        assertThat(clone2, equalTo(origin));
    }
}