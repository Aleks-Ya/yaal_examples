package yaml.streaming;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ParseStreamingTest {

    @Test
    public void parse() throws IOException {
        var yaml = "name: John\nage: 30";

        var factory = new YAMLFactory();
        var parser = factory.createParser(yaml);

        String parsedName = null;
        Integer parsedAge = null;

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
        }
        parser.close();

        assertThat(parsedName, equalTo("John"));
        assertThat(parsedAge, equalTo(30));
    }
}
