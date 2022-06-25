package lang.inheritance.linkage.early_and_late_linkage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Статические методы связываются на этапе компиляции.
 */
class StaticLinkageTest {
    @Test
    void test() {
        String str = ((StaticLinkageTest) null).getString();
        assertEquals("ok", str);
    }

    static String getString() {
        return "ok";
    }
}