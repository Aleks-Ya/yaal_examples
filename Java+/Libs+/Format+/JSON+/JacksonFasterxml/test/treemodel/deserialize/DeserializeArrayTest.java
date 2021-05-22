package treemodel.deserialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeserializeArrayTest {

    @Test
    public void parse() throws IOException {
        var json = "[" +
                "{\"name\":\"Tom\"}," +
                "{\"name\":\"John\"}" +
                "]";

        var sb = new StringBuilder();

        var mapper = new ObjectMapper();
        var arrayNode = (ArrayNode) mapper.readTree(json);
        for (var anArrayNode : arrayNode) {
            var objectNode = (ObjectNode) anArrayNode;
            var textNode = (TextNode) objectNode.get("name");
            var name = textNode.textValue();
            sb.append(name);
        }

        assertEquals(sb.toString(), "TomJohn");
    }

}
