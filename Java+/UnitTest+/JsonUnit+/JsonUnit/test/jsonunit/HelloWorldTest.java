package jsonunit;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonPartEquals;
import static net.javacrumbs.jsonunit.JsonAssert.when;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_VALUES;
import static net.javacrumbs.jsonunit.core.Option.TREATING_NULL_AS_ABSENT;

class HelloWorldTest {

    @Test
    void testJsonEquals() {
        assertJsonEquals("{\"test\":1}", "{\n\"test\": 1\n}");
    }

    @Test
    void testJsonPartEquals() {
        assertJsonPartEquals("2", "{\"test\":[{\"value\":1},{\"value\":2}]}",
                "test[1].value");
    }

    @Test
    void testJsonEqualsWithOptions() {
        assertJsonEquals("{\"test\":{\"a\":1}}",
                "{\"test\":{\"a\":1, \"b\": null}}",
                when(TREATING_NULL_AS_ABSENT));
    }

    @Test
    void testJsonEqualsIgnoringValues() {
        assertJsonEquals("[{\"test\":1}, {\"test\":2}]",
                "[{\n\"test\": 1\n}, {\"test\": 4}]", when(IGNORING_VALUES));
    }

    @Test
    void testLenientParsingOfJson() {
        assertJsonEquals("{//Look ma, no quotation marks\n test:'value'}",
                "{\n\"test\": \"value\"\n}");
    }

}
