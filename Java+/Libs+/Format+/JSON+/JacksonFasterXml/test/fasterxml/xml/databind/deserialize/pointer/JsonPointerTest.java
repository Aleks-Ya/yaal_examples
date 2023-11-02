package fasterxml.xml.databind.deserialize.pointer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Convert part of JSON to POJO.
 */
class JsonPointerTest {

    @Test
    void pointer() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{ 'person': {'id': 123, 'name': 'aleks'}}");
        var mapper = new ObjectMapper();
        var rootNode = mapper.readTree(json);
        var person = rootNode.at("/person");
        var user = mapper.treeToValue(person, User.class);

        assertThat(user.id).isEqualTo(123);
        assertThat(user.name).isEqualTo("aleks");
    }

    @SuppressWarnings("WeakerAccess")
    private static class User {
        public Integer id;
        public String name;
    }
}
