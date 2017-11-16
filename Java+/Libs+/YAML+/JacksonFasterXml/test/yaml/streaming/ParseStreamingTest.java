package yaml.streaming;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ParseStreamingTest {

    @Test
    public void parse() throws IOException {
        String yaml = "name: John\nage: 30";

        YAMLFactory factory = new YAMLFactory();
        YAMLParser parser = factory.createParser(yaml);

        String parsedName = null;
        Integer parsedAge = null;

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
        }
        parser.close();

        assertThat(parsedName, equalTo("John"));
        assertThat(parsedAge, equalTo(30));
    }
}
