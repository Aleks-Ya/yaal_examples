package utilt;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utilt.UuidAsserts.UUID_STRING;

class UuidAssertsTest {
    @Test
    void uuidCondition() {
        assertThat("32bbb954-d9ab-4047-bcfa-2febd0e09330").is(UUID_STRING);
        assertThat("abc").isNot(UUID_STRING);
        assertThat("").isNot(UUID_STRING);
        assertThat((String) null).isNot(UUID_STRING);
    }
}