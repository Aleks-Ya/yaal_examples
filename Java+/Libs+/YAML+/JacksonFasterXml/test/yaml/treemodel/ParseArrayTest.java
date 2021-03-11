package yaml.treemodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ParseArrayTest {

    @Test
    public void arrayOfStrings() throws IOException {
        var yaml = "- John\n" +
                "- Tom";
        var mapper = new ObjectMapper(new YAMLFactory());
        var arrayNode = (ArrayNode) mapper.readTree(yaml);

        var johnNode = (TextNode) arrayNode.get(0);
        var tomNode = (TextNode) arrayNode.get(1);

        assertThat(johnNode.textValue(), equalTo("John"));
        assertThat(tomNode.textValue(), equalTo("Tom"));
    }

    @Test
    public void arrayOfObjects() throws IOException {
        var yaml = ParseArrayTest.class.getResource("ParseArrayTest_arrayOfObjects.yaml");
        var mapper = new ObjectMapper(new YAMLFactory());
        var arrayNode = (ArrayNode) mapper.readTree(yaml);

        var johnElementNode = (ObjectNode) arrayNode.get(0);
        var johnNode = (ObjectNode) johnElementNode.get("John");
        var johnTitleNode = (TextNode) johnNode.get("title");

        var tomElementNode = (ObjectNode) arrayNode.get(1);
        var tomNode = (ObjectNode) tomElementNode.get("Tom");
        var tomTitleNode = (IntNode) tomNode.get("age");

        assertThat(johnTitleNode.textValue(), equalTo("officer"));
        assertThat(tomTitleNode.asInt(), equalTo(30));
    }
}
