package jsonunit;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonPartEquals;
import static net.javacrumbs.jsonunit.JsonAssert.when;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_VALUES;
import static net.javacrumbs.jsonunit.core.Option.TREATING_NULL_AS_ABSENT;

class HelloWorldTest {

    @Test
    void test() {
        // compares two JSON documents
        assertJsonEquals("{\"test\":1}", "{\n\"test\": 1\n}");

        // objects are automatically serialized before comparison
//        assertJsonEquals(jsonObject, "{\n\"test\": 1\n}");

        // compares only part
        assertJsonPartEquals("2", "{\"test\":[{\"value\":1},{\"value\":2}]}",
                "test[1].value");

        // extra options can be specified
        assertJsonEquals("{\"test\":{\"a\":1}}",
                "{\"test\":{\"a\":1, \"b\": null}}",
                when(TREATING_NULL_AS_ABSENT));

        // compares only the structure, not the values
        assertJsonEquals("[{\"test\":1}, {\"test\":2}]",
                "[{\n\"test\": 1\n}, {\"test\": 4}]", when(IGNORING_VALUES));

        // Lenient parsing of expected value
        assertJsonEquals("{//Look ma, no quotation marks\n test:'value'}",
                "{\n\"test\": \"value\"\n}");
    }

}
