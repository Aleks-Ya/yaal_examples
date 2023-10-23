package treemodel.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;

class SerializeObjectTest {
    @Test
    void recordClass() throws IOException, JSONException {
        var person = new Person("John", 30);
        var mapper = new ObjectMapper();
        var node = mapper.valueToTree(person);
        var actJson = mapper.writeValueAsString(node);
        var expJson = """
                {"name":"John","age":30}""";
        JSONAssert.assertEquals(expJson, actJson, true);
    }

    record Person(String name, Integer age) {
    }

    @Test
    void objectNode() throws IOException, JSONException {
        var mapper = new ObjectMapper();
        var rootNode = mapper.createObjectNode();
        rootNode.put("name", "John");
        rootNode.put("age", 30);
        var graduation = rootNode.putObject("graduation");
        graduation.put("institution", "Harvard");
        graduation.put("year", 2010);
        var actJson = mapper.writeValueAsString(rootNode);
        var expJson = """
                {"name":"John","age":30,"graduation":{"institution":"Harvard", "year": 2010}}""";
        JSONAssert.assertEquals(expJson, actJson, true);
    }
}
