package databind.deserialize.custom;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Customize property name.
 */
public class CustomPropertyNameTest {

    @Test
    public void test() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{'_id': 123}");
        var mapper = new ObjectMapper();

        var user = mapper.readValue(json, User.class);

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
