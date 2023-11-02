package fasterxml.xml.databind.deserialize.datatype;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DeserializeBigDecimalTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void object() throws IOException {
        var json = "0.7";
        var bigDecimal = mapper.readValue(json, BigDecimal.class);
        assertThat(bigDecimal).isEqualTo(BigDecimal.valueOf(0.7));
    }

    @Test
    void field() throws IOException {
        var json = """
                {"x":0.7,"y":123.95}""";
        var coordinatesAct = mapper.readValue(json, Coordinates.class);
        var coordinatesExp = new Coordinates(BigDecimal.valueOf(0.7), BigDecimal.valueOf(123.95));
        assertThat(coordinatesAct).isEqualTo(coordinatesExp);
    }

    record Coordinates(BigDecimal x, BigDecimal y) {
    }

}
