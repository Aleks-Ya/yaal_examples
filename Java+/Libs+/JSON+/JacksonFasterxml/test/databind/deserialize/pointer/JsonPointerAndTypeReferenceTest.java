package databind.deserialize.pointer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Convert part of JSON to POJO (using TypeReference for List parsing).
 */
public class JsonPointerAndTypeReferenceTest {

    @Test
    public void pointer() throws IOException {
        String json = "{ \"users\": [{\"name\": \"aleks\"}, {\"name\": \"john\"} ]}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        JsonNode userNode = rootNode.at("/users");
        String userJson = userNode.toString();
        System.out.println("Text:" + userJson);
        List<User> users = mapper.readValue(userJson, new TypeReference<List<User>>() {
        });

        assertThat(users, hasSize(2));
        assertThat(users.get(0).name, equalTo("aleks"));
        assertThat(users.get(1).name, equalTo("john"));
    }

    @SuppressWarnings("WeakerAccess")
    private static class User {
        public String name;
    }
}
