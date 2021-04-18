import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.JsonAssert.whenIgnoringPaths;

/**
 * Ignore specified JSON tokens from comparision.
 */
public class IgnoreTokens {

    @Test
    public void ignoreValue() {
        String exp = "{\"test\":\"${json-unit.ignore}\"}";
        String act = "{\"test\": {\"object\" : {\"another\" : 1}}}";
        assertJsonEquals(exp, act);
    }

    @Test
    public void ignorePath() {
        String exp = "{\"root\":{\"test\":1, \"ignored\": 2}}";
        String act = "{\"root\":{\"test\":1, \"ignored\": 1}}";
        assertJsonEquals(exp, act, whenIgnoringPaths("root.ignored"));
    }

}
