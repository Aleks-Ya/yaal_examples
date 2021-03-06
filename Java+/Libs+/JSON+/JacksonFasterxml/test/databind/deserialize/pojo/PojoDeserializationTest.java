package databind.deserialize.pojo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import util.JsonUtil;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Deserialize JSON to POJO.
 */
public class PojoDeserializationTest {

    @Test
    public void readValue() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{'id': 123, 'name': 'aleks'}");
        var mapper = new ObjectMapper();

        var user = mapper.readValue(json, User.class);

        assertThat(user.getId(), equalTo(123));
        assertThat(user.getName(), equalTo("aleks"));
    }

    /**
     * Skip unknown fields.
     */
    @Test
    public void failOnUnknownProperties() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{'id': 123, 'name': 'aleks', 'age': 30}");
        var mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        var user = mapper.readValue(json, User.class);

        assertThat(user.getId(), equalTo(123));
        assertThat(user.getName(), equalTo("aleks"));
    }

    @SuppressWarnings("unused")
    private static class User {
        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}
