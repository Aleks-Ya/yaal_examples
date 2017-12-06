package databind.serialize.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Mix-In class is an interface.
 */
public class InterfaceMixInTest {

    @Test
    public void serialize() throws IOException {
        Address address = new Address();
        address.city = "SPb";
        address.state = "Leningrad";

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(address);

        assertThat(json, containsString("state"));

        mapper.addMixIn(Address.class, AddressMixin.class);

        Address deserializedUser = mapper.readValue(json, Address.class);

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
