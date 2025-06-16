package fasterxml.xml.databind.treemodel.deserialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class MissingNodeTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void findMissingNode() throws IOException {
        var json = """
                {"a": 1}""";
        var root = mapper.readTree(json);
        var missingNode = root.findPath("absent");
        assertThat(missingNode.isMissingNode()).isTrue();
        assertThat(missingNode.asText()).isEqualTo("");
    }

    @Test
    void findNestedMissingNode() throws IOException {
        var json = """
                {"a": 1}""";
        var root = mapper.readTree(json);
        var missingNode = root.findPath("absent1").findPath("absent2");
        assertThat(missingNode.isMissingNode()).isTrue();
        assertThat(missingNode.asText()).isEqualTo("");
    }

    @Test
    void missingNodeAs() throws IOException {
        var json = """
                {"a": 1}""";
        var root = mapper.readTree(json);
        var missingNode = root.findPath("absent");
        assertThat(missingNode.asBoolean()).isFalse();
        assertThat(missingNode.asText()).isEqualTo("");
        assertThat(missingNode.asInt()).isEqualTo(0);
        assertThat(missingNode.asLong()).isEqualTo(0L);
        assertThat(missingNode.asDouble()).isEqualTo(0D);
    }
}
