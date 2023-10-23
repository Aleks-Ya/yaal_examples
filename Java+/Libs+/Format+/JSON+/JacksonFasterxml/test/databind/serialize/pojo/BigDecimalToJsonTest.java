package databind.serialize.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

class BigDecimalToJsonTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void object() throws IOException {
        var bigDecimal = BigDecimal.valueOf(0.7);
        var json = mapper.writeValueAsString(bigDecimal);
        var exp = "0.7";
        assertJsonEquals(exp, json);
    }

    @Test
    void field() throws IOException {
        var coordinates = new Coordinates(BigDecimal.valueOf(0.7), BigDecimal.valueOf(123.95));
        var jsonAct = mapper.writeValueAsString(coordinates);
        var jsonExp = """
                {"x":0.7,"y":123.95}""";
        assertJsonEquals(jsonExp, jsonAct);
    }

    record Coordinates(BigDecimal x, BigDecimal y) {
    }

}
