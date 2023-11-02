package fasterxml.xml.databind.serialize.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class MapToJsonTest {

    @Test
    void test() throws IOException {
        var mapper = new ObjectMapper();
        var map =
                Map.of("id", 1, "name", "John",
                        "details", Map.of("age", 30, "working", true));
        var act = mapper.writeValueAsString(map);
        assertThatJson(act).isEqualTo("{'id':1,'name':'John','details': {'age': 30, 'working': true}}");
    }

}
