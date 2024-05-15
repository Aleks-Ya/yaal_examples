package fasterxml.xml.databind.deserialize.datatype;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DeserializeMapOfMapsTest {
    @Test
    void deserialize() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{" +
                "'metadata1': {'city': 'London', 'country': 'UK' }, " +
                "'metadata2': {'street': 'Baker', 'country': 'England' }" +
                "}");
        var mapper = new ObjectMapper();
        var mapOfMaps = mapper.readValue(json, new TypeReference<Map<String, Map<String, String>>>() {
        });
        System.out.println(mapOfMaps);
        assertThat(mapOfMaps).containsExactlyInAnyOrderEntriesOf(Map.of(
                "metadata1", Map.of("city", "London", "country", "UK"),
                "metadata2", Map.of("street", "Baker", "country", "England"))
        );
    }
}
