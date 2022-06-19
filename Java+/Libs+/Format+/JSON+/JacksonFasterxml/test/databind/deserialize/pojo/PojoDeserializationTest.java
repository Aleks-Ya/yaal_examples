package databind.deserialize.pojo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Deserialize JSON to POJO.
 */
class PojoDeserializationTest {

    @Test
    void readValue() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{'id': 123, 'name': 'aleks'}");
        var mapper = new ObjectMapper();

        var user = mapper.readValue(json, User.class);

        assertThat(user.getId()).isEqualTo(123);
        assertThat(user.getName()).isEqualTo("aleks");
    }

    /**
     * Skip unknown fields.
     */
    @Test
    void failOnUnknownProperties() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{'id': 123, 'name': 'aleks', 'age': 30}");
        var mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        var user = mapper.readValue(json, User.class);

        assertThat(user.getId()).isEqualTo(123);
        assertThat(user.getName()).isEqualTo("aleks");
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
