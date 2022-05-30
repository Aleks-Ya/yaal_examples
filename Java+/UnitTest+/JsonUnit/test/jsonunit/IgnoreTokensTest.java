package jsonunit;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.JsonAssert.whenIgnoringPaths;

/**
 * Ignore specified JSON tokens from comparision.
 */
class IgnoreTokensTest {

    @Test
    void ignoreValue() {
        var exp = "{\"test\":\"${json-unit.ignore}\"}";
        var act = "{\"test\": {\"object\" : {\"another\" : 1}}}";
        assertJsonEquals(exp, act);
    }

    @Test
    void ignorePath() {
        var exp = "{\"root\":{\"test\":1, \"ignored\": 2}}";
        var act = "{\"root\":{\"test\":1, \"ignored\": 1}}";
        assertJsonEquals(exp, act, whenIgnoringPaths("root.ignored"));
    }

}
