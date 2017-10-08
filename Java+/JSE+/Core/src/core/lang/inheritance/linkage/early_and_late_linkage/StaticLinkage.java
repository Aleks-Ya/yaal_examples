package core.lang.inheritance.linkage.early_and_late_linkage;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Статические методы связываются на этапе компиляции.
 */
public class StaticLinkage {
    @Test
    public void test() {
        String str = ((StaticLinkage) null).getString();
        Assert.assertEquals("ok", str);
    }

    static String getString() {
        return "ok";
    }
}