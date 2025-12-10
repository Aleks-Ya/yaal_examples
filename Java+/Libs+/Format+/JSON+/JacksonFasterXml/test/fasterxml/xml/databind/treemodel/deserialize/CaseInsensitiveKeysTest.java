package fasterxml.xml.databind.treemodel.deserialize;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CaseInsensitiveKeysTest {
    @Test
    void getCaseInsensitive() throws IOException {
        var json = """
                {"a": 1, "b": "abc" }""";
        var mapper = new ObjectMapper();
        var root = mapper.readTree(json);
        assertThat(getPropertyCaseInsensitive(root, "A").asInt()).isEqualTo(1);
        assertThat(getPropertyCaseInsensitive(root, "a").asInt()).isEqualTo(1);
        assertThat(getPropertyCaseInsensitive(root, "b").asText()).isEqualTo("abc");
        assertThat(getPropertyCaseInsensitive(root, "B").asText()).isEqualTo("abc");
        assertThat(getPropertyCaseInsensitive(root, "C").isNull()).isTrue();
    }

    private JsonNode getPropertyCaseInsensitive(JsonNode parentNode, String keyCaseInsensitive) {
        return parentNode.properties().stream()
                .filter(node -> keyCaseInsensitive.equalsIgnoreCase(node.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseGet(NullNode::getInstance);
    }
}
