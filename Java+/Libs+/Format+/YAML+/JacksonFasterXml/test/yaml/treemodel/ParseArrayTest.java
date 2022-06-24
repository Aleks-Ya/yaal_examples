package yaml.treemodel;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class ParseArrayTest {

    @Test
    void arrayOfStrings() throws IOException {
        var yaml = "- John\n" +
                "- Tom";
        var mapper = new YAMLMapper();
        var arrayNode = (ArrayNode) mapper.readTree(yaml);

        var johnNode = (TextNode) arrayNode.get(0);
        var tomNode = (TextNode) arrayNode.get(1);

        assertThat(johnNode.textValue()).isEqualTo("John");
        assertThat(tomNode.textValue()).isEqualTo("Tom");
    }

    @Test
    void arrayOfObjects() throws IOException {
        var yaml = ParseArrayTest.class.getResource("ParseArrayTest_arrayOfObjects.yaml");
        var mapper = new YAMLMapper();
        var arrayNode = (ArrayNode) mapper.readTree(yaml);

        var johnElementNode = (ObjectNode) arrayNode.get(0);
        var johnNode = (ObjectNode) johnElementNode.get("John");
        var johnTitleNode = (TextNode) johnNode.get("title");

        var tomElementNode = (ObjectNode) arrayNode.get(1);
        var tomNode = (ObjectNode) tomElementNode.get("Tom");
        var tomTitleNode = (IntNode) tomNode.get("age");

        assertThat(johnTitleNode.textValue()).isEqualTo("officer");
        assertThat(tomTitleNode.asInt()).isEqualTo(30);
    }
}
