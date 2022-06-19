package databind.serialize.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

class MapToJsonTest {

    @Test
    void test() throws IOException {
        var mapper = new ObjectMapper();
        var map =
                Map.of("id", 1, "name", "John",
                        "details", Map.of("age", 30, "working", true));
        var act = mapper.writeValueAsString(map);
        var exp = "{'id':1,'name':'John','details': {'age': 30, 'working': true}}";
        assertJsonEquals(exp, act);
    }

}
