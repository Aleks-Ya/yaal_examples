package treemodel.deserialize;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DeserializeArrayTest {

    @Test
    public void parse() throws IOException {
        String json = "[" +
                "{\"name\":\"Tom\"}," +
                "{\"name\":\"John\"}" +
                "]";

        StringBuilder sb = new StringBuilder();

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = (ArrayNode) mapper.readTree(json);
        for (JsonNode anArrayNode : arrayNode) {
            ObjectNode objectNode = (ObjectNode) anArrayNode;
            TextNode textNode = (TextNode) objectNode.get("name");
            String name = textNode.textValue();
            sb.append(name);
        }

        assertEquals(sb.toString(), "TomJohn");
    }

}
