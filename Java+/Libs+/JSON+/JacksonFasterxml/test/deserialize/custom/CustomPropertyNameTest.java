package deserialize.custom;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Customize property name.
 */
public class CustomPropertyNameTest {

    @Test
    public void test() throws IOException {
        String json = "{\"_id\": 123}";
        ObjectMapper mapper = new ObjectMapper();

        User user = mapper.readValue(json, User.class);

        assertThat(user.getId(), equalTo(123));
    }

    private static class User {
        @JsonProperty("_id")
        private Integer id;

        Integer getId() {
            return id;
        }

        @SuppressWarnings("unused")
        private void setId(Integer id) {
            this.id = id;
        }
    }

}
