package databind.deserialize.mixin.ignore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import util.JsonUtil;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Source: https://dzone.com/articles/jackson-mixin-to-the-rescue
 */
public class IgnoreFieldTest {

    @Test
    public void deserialize() throws IOException {
        String fullJson = JsonUtil.singleQuoteToDouble("{'city': 'SPb', 'state': 'Leningrad'}");

        ObjectMapper mapper = new ObjectMapper();

        assertThat(fullJson, containsString("state"));

        mapper.addMixIn(Address.class, AddressMixin.class);

        Address deserializedUser = mapper.readValue(fullJson, Address.class);
        assertThat(deserializedUser.state, nullValue());
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
