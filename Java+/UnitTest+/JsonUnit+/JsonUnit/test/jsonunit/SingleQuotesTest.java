package jsonunit;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

/**
 * Can use single quotes only in expected JSON.
 */
class SingleQuotesTest {

    @Test
    void singleQuotesInFieldNameAndValue() {
        var exp = "{'test': 'abc'}";
        var act = "{\"test\": \"abc\"}";
        assertJsonEquals(exp, act);
    }

    @Test
    void singleQuotesInValueOnly() {
        var exp = "{test: 'abc'}";
        var act = "{\"test\": \"abc\"}";
        assertJsonEquals(exp, act);
    }
}
