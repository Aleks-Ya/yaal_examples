package deserialization.parser;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ParseSingleFieldTest {

    @Test
    void extractField() {
        var json = "{\"person\": {\"name\":\"John\", \"age\": 30}}";
        var object = JsonParser.parseString(json).getAsJsonObject();
        var age = object.get("person").getAsJsonObject().get("age").getAsInt();
        assertThat(age).isEqualTo(30);
    }

}
