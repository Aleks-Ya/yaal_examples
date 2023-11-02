package jsonunit.assertj;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class LenientExpectedValueTest {
    @Test
    void test() {
        assertThatJson("{\"a\":\"1\", \"b\":2}").isEqualTo("{b:2, a:'1'}");
    }
}
