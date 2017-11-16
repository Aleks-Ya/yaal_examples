package yaml.treemodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ParseArrayTest {

    @Test
    public void arrayOfStrings() throws IOException {
        String yaml = "- John\n" +
                "- Tom";
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ArrayNode arrayNode = (ArrayNode) mapper.readTree(yaml);

        TextNode johnNode = (TextNode) arrayNode.get(0);
        TextNode tomNode = (TextNode) arrayNode.get(1);

        assertThat(johnNode.textValue(), equalTo("John"));
        assertThat(tomNode.textValue(), equalTo("Tom"));
    }

    @Test
    public void arrayOfObjects() throws IOException {
        URL yaml = ParseArrayTest.class.getResource("ParseArrayTest_arrayOfObjects.yaml");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ArrayNode arrayNode = (ArrayNode) mapper.readTree(yaml);

        ObjectNode johnElementNode = (ObjectNode) arrayNode.get(0);
        ObjectNode johnNode = (ObjectNode) johnElementNode.get("John");
        TextNode johnTitleNode = (TextNode) johnNode.get("title");

        ObjectNode tomElementNode = (ObjectNode) arrayNode.get(1);
        ObjectNode tomNode = (ObjectNode) tomElementNode.get("Tom");
        IntNode tomTitleNode = (IntNode) tomNode.get("age");

        assertThat(johnTitleNode.textValue(), equalTo("officer"));
        assertThat(tomTitleNode.asInt(), equalTo(30));
    }
}
