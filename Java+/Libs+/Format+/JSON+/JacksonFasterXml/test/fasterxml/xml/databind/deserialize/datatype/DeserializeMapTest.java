package fasterxml.xml.databind.deserialize.datatype;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DeserializeMapTest {
    @Test
    void deserialize() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{'city': 'London', 'country': 'UK' }");
        var mapper = new ObjectMapper();
        var map = mapper.readValue(json, new TypeReference<Map<String, String>>() {
        });
        System.out.println(map);
        assertThat(map).containsExactlyInAnyOrderEntriesOf(Map.of("city", "London", "country", "UK"));
    }
}
