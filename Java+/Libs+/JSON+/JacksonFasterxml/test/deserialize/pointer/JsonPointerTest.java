package deserialize.pointer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Convert part of JSON to POJO.
 */
public class JsonPointerTest {

    @Test
    public void pointer() throws IOException {
        String json = "{ \"person\": {\"id\": 123, \"name\": \"aleks\"}}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        JsonNode person = rootNode.at("/person");
        User user = mapper.treeToValue(person, User.class);

        assertThat(user.getId(), equalTo(123));
        assertThat(user.getName(), equalTo("aleks"));
    }

    @SuppressWarnings("unused")
    private static class User {
        private Integer id;
        private String name;

        Integer getId() {
            return id;
        }

        private void setId(Integer id) {
            this.id = id;
        }

        String getName() {
            return name;
        }

        private void setName(String name) {
            this.name = name;
        }
    }
}
