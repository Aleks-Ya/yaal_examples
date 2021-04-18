package util.date;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

/**
 * Different dates are equal.
 */
public class WrongDateComparing {


    @Test
    public void defaultLocal() {
        Date d1 = Date.valueOf("1582-10-10");
        Date d2 = Date.valueOf("1582-10-20");
        assertEquals(d1, d2);
    }

}