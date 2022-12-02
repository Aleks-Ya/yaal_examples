package lang.inheritance.linkage.early_and_late_linkage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Статические методы связываются на этапе компиляции.
 */
class StaticLinkageTest {
    @Test
    void test() {
        String str = ((StaticLinkageTest) null).getString();
        assertThat(str).isEqualTo("ok");
    }

    static String getString() {
        return "ok";
    }
}