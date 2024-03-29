package fasterxml.xml.streaming.deserialize;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonToken;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DeserializeTest {

    @Test
    void parse() throws IOException {
        var json = "{\"name\":\"Tom\",\"age\":25,\"address\":[\"Poland\",\"5th avenue\"]}";
        var factory = new JsonFactory();
        var parser = factory.createParser(json);

        String parsedName = null;
        Integer parsedAge = null;
        List<String> addresses = new ArrayList<>();

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            var fieldName = parser.getCurrentName();
            if ("name".equals(fieldName)) {
                parser.nextToken();
                parsedName = parser.getText();
            }

            if ("age".equals(fieldName)) {
                parser.nextToken();
                parsedAge = parser.getIntValue();
            }

            if ("address".equals(fieldName)) {
                parser.nextToken();
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    addresses.add(parser.getText());
                }
            }
        }
        parser.close();

        assertThat(parsedName).isEqualTo("Tom");
        assertThat(parsedAge).isEqualTo((Integer) 25);
        assertThat(addresses).isEqualTo(List.of("Poland", "5th avenue"));
    }

}
