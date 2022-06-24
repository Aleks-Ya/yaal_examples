package jsonassert;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.ValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.skyscreamer.jsonassert.JSONCompare.compareJSON;

class CustomizationTest {

    @Test
    void anyValueByPath() throws JSONException {
        var actual = "{first: actual, second:1}";
        var expected = "{first: expected, second: 1}";

        var comparator = (ValueMatcher<Object>) (o1, o2) -> true;

        var custom = Customization.customization("first", comparator);
        JSONComparator jsonCmp = new CustomComparator(JSONCompareMode.STRICT, custom);
        var result = compareJSON(expected, actual, jsonCmp);
        assertTrue(result.passed(), result.getMessage());
    }

    @Test
    void customCompareValueByPath() throws JSONException {
        var actual = "{first: actual, second:1}";
        var expected = "{first: expected, second: 1}";

        var comparator = (ValueMatcher<Object>) (o1, o2) -> o1.toString().equals("actual") && o2.toString().equals("expected");

        var custom = Customization.customization("first", comparator);
        JSONComparator jsonCmp = new CustomComparator(JSONCompareMode.STRICT, custom);
        var result = compareJSON(expected, actual, jsonCmp);
        assertTrue(result.passed(), result.getMessage());
    }
}
