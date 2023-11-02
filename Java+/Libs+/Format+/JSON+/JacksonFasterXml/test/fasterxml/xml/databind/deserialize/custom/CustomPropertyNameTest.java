package fasterxml.xml.databind.deserialize.custom;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Customize property name.
 */
class CustomPropertyNameTest {

    @Test
    void test() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{'_id': 123}");
        var mapper = new ObjectMapper();
        var user = mapper.readValue(json, User.class);
        assertThat(user.getId()).isEqualTo(123);
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
