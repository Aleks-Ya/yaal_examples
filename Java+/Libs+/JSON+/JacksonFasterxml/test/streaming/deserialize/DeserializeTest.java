package streaming.deserialize;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeserializeTest {

    @Test
    public void parse() throws IOException {
        String json = "{\"name\":\"Tom\",\"age\":25,\"address\":[\"Poland\",\"5th avenue\"]}";
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(json);

        String parsedName = null;
        Integer parsedAge = null;
        List<String> addresses = new LinkedList<>();

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = parser.getCurrentName();
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

        assertEquals(parsedName, "Tom");
        assertEquals(parsedAge, (Integer) 25);
        assertEquals(addresses, Arrays.asList("Poland", "5th avenue"));
    }

}
