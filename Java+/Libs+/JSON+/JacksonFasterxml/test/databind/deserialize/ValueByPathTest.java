package databind.deserialize;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Extract value from JSON by path.
 */
public class ValueByPathTest {

    @Test
    public void at() throws IOException {
        String json = "{\"a\": 1, \"b\": \"abc\", \"c\": {\"d\": true}}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        assertThat(root.at("/a").asInt(), equalTo(1));
        assertThat(root.at("/b").asText(), equalTo("abc"));
        assertThat(root.at("/c/d").asBoolean(), equalTo(true));
    }

}
