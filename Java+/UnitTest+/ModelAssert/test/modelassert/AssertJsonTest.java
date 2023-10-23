package modelassert;

import org.junit.jupiter.api.Test;

import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

class AssertJsonTest {
    @Test
    void wholeJson() {
        assertJson("{\"name\":\"ModelAssert\",\"versions\":[1.00, 1.01, 1.02]}")
                .isEqualTo("{\"name\":\"ModelAssert\",\"versions\":[1.00, 1.01, 1.02]}");
    }
}
