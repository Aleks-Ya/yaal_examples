package fasterxml.xml.databind.treemodel.deserialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class NullNodeTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void getNullNode() throws IOException {
        var json = """
                {"a": null}""";
        var root = mapper.readTree(json);
        var nullNode = root.get("a");
        assertThat(nullNode).isInstanceOf(NullNode.class);
        assertThat(nullNode.isNull()).isTrue();
        assertThat(nullNode.isMissingNode()).isFalse();
        assertThat(nullNode.asText()).isEqualTo("null");
    }

    @Test
    void getNestedNullNode() throws IOException {
        var json = """
                {"a": null}""";
        var root = mapper.readTree(json);
        var nestedNullNode = root.get("a").get("b");
        assertThat(nestedNullNode).isNull();
    }

    @Test
    void missingNodeAs() throws IOException {
        var json = """
                {"a": null}""";
        var root = mapper.readTree(json);
        var nullNode = root.get("a");

        assertThat(nullNode).isInstanceOf(NullNode.class);
        assertThat(nullNode.isNull()).isTrue();
        assertThat(nullNode.isMissingNode()).isFalse();

        assertThat(nullNode.asBoolean()).isFalse();
        assertThat(nullNode.asText()).isEqualTo("null");
        assertThat(nullNode.asInt()).isEqualTo(0);
        assertThat(nullNode.asLong()).isEqualTo(0L);
        assertThat(nullNode.asDouble()).isEqualTo(0D);
    }
}
