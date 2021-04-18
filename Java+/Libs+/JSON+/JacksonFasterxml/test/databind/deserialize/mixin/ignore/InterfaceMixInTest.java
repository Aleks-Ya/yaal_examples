package databind.deserialize.mixin.ignore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.nullValue;

/**
 * Mix-In class is an interface.
 */
public class InterfaceMixInTest {

    @Test
    public void deserialize() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{'city': 'SPb', 'state': 'Leningrad'}");
        var mapper = new ObjectMapper();
        assertThat(json, containsString("state"));
        mapper.addMixIn(Address.class, AddressMixin.class);
        var deserializedUser = mapper.readValue(json, Address.class);
        assertThat(deserializedUser.state, nullValue());
    }

    @SuppressWarnings("unused")
    private interface AddressMixin {
        String getCity();

        @JsonIgnore
        String getState();

    }

    @SuppressWarnings("unused")
    private static final class Address {
        private String city;
        private String state;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
