package deserialization.parser;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

class ParseSingleField {

    @Test
    void extractField() {
        var json = "{\"person\": {\"name\":\"John\", \"age\": 30}}";
        var object = JsonParser.parseString(json).getAsJsonObject();
        var age = object.get("person").getAsJsonObject().get("age").getAsInt();
        assertThat(age, equalTo(30));
    }

}
