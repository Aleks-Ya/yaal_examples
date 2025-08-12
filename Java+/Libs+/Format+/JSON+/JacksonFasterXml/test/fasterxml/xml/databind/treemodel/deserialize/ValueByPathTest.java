package fasterxml.xml.databind.treemodel.deserialize;

import com.fasterxml.jackson.databind.JsonNode;
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
                {"a": 1, "b": "abc", "c": {"d": true}, "e": ["A","B"] }""";
        var mapper = new ObjectMapper();
        var root = mapper.readTree(json);
        assertThat(root.at("/a").asInt()).isEqualTo(1);
        assertThat(root.at("/b").asText()).isEqualTo("abc");
        assertThat(root.at("/c/d").asBoolean()).isTrue();
        assertThat(root.at("/absent/d").isMissingNode()).isTrue();
        assertThat(root.at("/e").isArray()).isTrue();
        assertThat(root.at("/e").valueStream().map(JsonNode::textValue).toList()).containsExactly("A", "B");
    }

    @Test
    void findPath() throws IOException {
        var json = """
                {"a": 1, "b": "abc", "c": {"d": true}}""";
        var mapper = new ObjectMapper();
        var root = mapper.readTree(json);
        assertThat(root.findPath("a").asInt()).isEqualTo(1);
        assertThat(root.findPath("b").asText()).isEqualTo("abc");
        assertThat(root.findPath("c").findPath("d").asBoolean()).isTrue();
        assertThat(root.findPath("absent").findPath("d").isMissingNode()).isTrue();
    }
}
