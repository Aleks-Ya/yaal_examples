package deserialize.datatype;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Deserialize a JSON array (that has NOT field name) to Java Array, List, Set.
 */
public class DeserializeArrayWithoutFieldNameTest {

    @Test
    public void stringArrayToList() throws IOException {
        String json = "[\"aleks\", \"john\"]";
        ObjectMapper mapper = new ObjectMapper();
        List<String> users = mapper.readValue(json, new TypeReference<List<String>>() {
        });
        assertThat(users, hasSize(2));
        assertThat(users, hasItems("aleks", "john"));
    }

    @Test
    public void objectArrayToList() throws IOException {
        String json = "[ {\"name\": \"aleks\"}, { \"name\": \"john\"}]";
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.readValue(json, new TypeReference<List<User>>() {
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
