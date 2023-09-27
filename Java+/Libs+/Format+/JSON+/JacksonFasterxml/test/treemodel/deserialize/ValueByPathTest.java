package treemodel.deserialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Extract value from JSON by path.
 */
class ValueByPathTest {
    @Test
    void at() throws IOException {
        var json = """
                {"a": 1, "b": "abc", "c": {"d": true}}""";
        var mapper = new ObjectMapper();
        var root = mapper.readTree(json);
        assertThat(root.at("/a").asInt()).isEqualTo(1);
        assertThat(root.at("/b").asText()).isEqualTo("abc");
        assertThat(root.at("/c/d").asBoolean()).isTrue();
        assertThat(root.at("/absent/d").isMissingNode()).isTrue();
    }
}
