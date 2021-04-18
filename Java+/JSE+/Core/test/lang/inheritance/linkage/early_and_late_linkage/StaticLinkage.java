package lang.inheritance.linkage.early_and_late_linkage;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * Статические методы связываются на этапе компиляции.
 */
public class StaticLinkage {
    @Test
    public void test() {
        String str = ((StaticLinkage) null).getString();
        assertEquals("ok", str);
    }

    static String getString() {
        return "ok";
    }
}