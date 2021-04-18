import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.ValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.skyscreamer.jsonassert.JSONCompare.compareJSON;

public class CustomizationTest {

    @Test
    public void anyValueByPath() throws JSONException {
        String actual = "{first: actual, second:1}";
        String expected = "{first: expected, second: 1}";

        ValueMatcher<Object> comparator = (o1, o2) -> true;

        Customization custom = Customization.customization("first", comparator);
        JSONComparator jsonCmp = new CustomComparator(JSONCompareMode.STRICT, custom);
        JSONCompareResult result = compareJSON(expected, actual, jsonCmp);
        assertTrue(result.passed(), result.getMessage());
    }

    @Test
    public void customCompareValueByPath() throws JSONException {
        String actual = "{first: actual, second:1}";
        String expected = "{first: expected, second: 1}";

        ValueMatcher<Object> comparator = (o1, o2) -> o1.toString().equals("actual") && o2.toString().equals("expected");

        Customization custom = Customization.customization("first", comparator);
        JSONComparator jsonCmp = new CustomComparator(JSONCompareMode.STRICT, custom);
        JSONCompareResult result = compareJSON(expected, actual, jsonCmp);
        assertTrue(result.passed(), result.getMessage());
    }
}
