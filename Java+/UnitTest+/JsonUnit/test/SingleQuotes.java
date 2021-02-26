import org.junit.Test;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

/**
 * Can use single quotes only in expected JSON.
 */
public class SingleQuotes {

    @Test
    public void singleQuotesInFieldNameAndValue() {
        String exp = "{'test': 'abc'}";
        String act = "{\"test\": \"abc\"}";
        assertJsonEquals(exp, act);
    }

    @Test
    public void singleQuotesInValueOnly() {
        String exp = "{test: 'abc'}";
        String act = "{\"test\": \"abc\"}";
        assertJsonEquals(exp, act);
    }
}
