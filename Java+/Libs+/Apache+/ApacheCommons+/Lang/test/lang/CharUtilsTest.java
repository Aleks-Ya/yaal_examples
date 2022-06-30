package lang;

import org.apache.commons.lang3.CharUtils;
import org.junit.jupiter.api.Test;

class CharUtilsTest {
    @Test
    void testName() {
        var c = CharUtils.toChar('\u0410');
        System.out.println(c);
        System.out.println('\u0410');
    }
}
