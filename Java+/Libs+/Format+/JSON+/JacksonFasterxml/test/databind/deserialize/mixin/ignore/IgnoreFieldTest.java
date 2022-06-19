package databind.deserialize.mixin.ignore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source: https://dzone.com/articles/jackson-mixin-to-the-rescue
 */
class IgnoreFieldTest {

    @Test
    void deserialize() throws IOException {
        var fullJson = JsonUtil.singleQuoteToDouble("{'city': 'SPb', 'state': 'Leningrad'}");
        var mapper = new ObjectMapper();
        assertThat(fullJson).containsSubsequence("state");
        mapper.addMixIn(Address.class, AddressMixin.class);
        var deserializedUser = mapper.readValue(fullJson, Address.class);
        assertThat(deserializedUser.state).isNull();
    }

    @SuppressWarnings("WeakerAccess")
    private static final class Address {
        public String city;
        public String state;
    }

    @SuppressWarnings("unused")
    private static abstract class AddressMixin {
        public String city;
        @JsonIgnore
        public String state;

    }
}
