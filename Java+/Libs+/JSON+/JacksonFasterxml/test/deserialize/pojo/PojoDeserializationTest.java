package deserialize.pojo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Deserialize JSON to POJO.
 */
public class PojoDeserializationTest {

    @Test
    public void readValue() throws IOException {
        String json = "{\"id\": 123, \"name\": \"aleks\"}";
        ObjectMapper mapper = new ObjectMapper();

        User user = mapper.readValue(json, User.class);

        assertThat(user.getId(), equalTo(123));
        assertThat(user.getName(), equalTo("aleks"));
    }

    /**
     * Skip unknown fields.
     */
    @Test
    public void failOnUnknownProperties() throws IOException {
        String json = "{\"id\": 123, \"name\": \"aleks\", \"age\": 30}";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        User user = mapper.readValue(json, User.class);

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
