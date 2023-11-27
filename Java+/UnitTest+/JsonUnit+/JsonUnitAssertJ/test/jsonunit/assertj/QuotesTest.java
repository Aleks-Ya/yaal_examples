package jsonunit.assertj;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class QuotesTest {
    @Test
    void singleQuotesInFieldNameAndValue() {
        assertThatJson("{\"test\": \"abc\"}").isEqualTo("{'test': 'abc'}");
    }

    @Test
    void singleQuotesInValueOnly() {
        assertThatJson("{\"test\": \"abc\"}").isEqualTo("{test: 'abc'}");
    }

    @Test
    void noQuotesInNameAndValue() {
        assertThatJson("{\"test\": \"abc\"}").isEqualTo("{test: abc}");
    }

    @Test
    void singleQuotesAndNoQuotesMix() {
        assertThatJson("{\"test\": \"abc\", \"name\": \"John Smith\"}")
                .isEqualTo("{test: abc, name: 'John Smith'}");
    }
}
