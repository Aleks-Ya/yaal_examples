package jsonunit.assertj;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class EqualsTest {
    @Test
    void test() {
        assertThatJson("{\n\"test\": 1\n}").isEqualTo("{\"test\":1}");
    }
}
