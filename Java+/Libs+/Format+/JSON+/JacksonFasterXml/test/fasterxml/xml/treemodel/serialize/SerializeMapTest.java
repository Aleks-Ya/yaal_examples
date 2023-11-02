package fasterxml.xml.treemodel.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class SerializeMapTest {
    @Test
    void asObject() throws IOException {
        var map = Map.of("name", "John", "age", 30);
        var mapper = new ObjectMapper();
        var node = mapper.valueToTree(map);
        var actJson = mapper.writeValueAsString(node);
        assertThatJson(actJson).isEqualTo("""
                {"name":"John","age":30}""");
    }
}
