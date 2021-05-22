package databind.deserialize.pointer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Convert part of JSON to POJO.
 */
public class JsonPointerTest {

    @Test
    public void pointer() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{ 'person': {'id': 123, 'name': 'aleks'}}");
        var mapper = new ObjectMapper();
        var rootNode = mapper.readTree(json);
        var person = rootNode.at("/person");
        var user = mapper.treeToValue(person, User.class);

        assertThat(user.id, equalTo(123));
        assertThat(user.name, equalTo("aleks"));
    }

    @SuppressWarnings("WeakerAccess")
    private static class User {
        public Integer id;
        public String name;
    }
}
