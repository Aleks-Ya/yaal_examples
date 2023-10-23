package treemodel.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.Map;

class SerializeMapTest {
    @Test
    void asObject() throws IOException, JSONException {
        var map = Map.of("name", "John", "age", 30);
        var mapper = new ObjectMapper();
        var node = mapper.valueToTree(map);
        var actJson = mapper.writeValueAsString(node);
        String expJson = """
                {"name":"John","age":30}""";
        JSONAssert.assertEquals(expJson, actJson, true);
    }
}
