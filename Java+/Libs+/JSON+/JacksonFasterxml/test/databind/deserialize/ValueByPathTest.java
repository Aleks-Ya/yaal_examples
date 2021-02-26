package databind.deserialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import util.JsonUtil;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Extract value from JSON by path.
 */
public class ValueByPathTest {

    @Test
    public void at() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{'a': 1, 'b': 'abc', 'c': {'d': true}}");
        var mapper = new ObjectMapper();
        var root = mapper.readTree(json);
        assertThat(root.at("/a").asInt(), equalTo(1));
        assertThat(root.at("/b").asText(), equalTo("abc"));
        assertThat(root.at("/c/d").asBoolean(), equalTo(true));
    }

}
