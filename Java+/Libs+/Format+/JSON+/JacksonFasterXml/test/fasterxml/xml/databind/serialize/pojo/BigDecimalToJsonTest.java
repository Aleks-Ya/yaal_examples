package fasterxml.xml.databind.serialize.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class BigDecimalToJsonTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void object() throws IOException {
        var bigDecimal = BigDecimal.valueOf(0.7);
        var json = mapper.writeValueAsString(bigDecimal);
        assertThatJson(json).isEqualTo("0.7");
    }

    @Test
    void field() throws IOException {
        var coordinates = new Coordinates(BigDecimal.valueOf(0.7), BigDecimal.valueOf(123.95));
        var jsonAct = mapper.writeValueAsString(coordinates);
        assertThatJson(jsonAct).isEqualTo("""
                {"x":0.7,"y":123.95}""");
    }

    record Coordinates(BigDecimal x, BigDecimal y) {
    }

}
