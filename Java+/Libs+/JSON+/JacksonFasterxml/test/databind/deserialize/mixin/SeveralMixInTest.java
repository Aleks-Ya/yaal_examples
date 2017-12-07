package databind.deserialize.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import util.JsonUtil;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Use several mix-ins for different deserialization.
 */
public class SeveralMixInTest {

    @Test
    public void deserialize() throws IOException {
        String json = JsonUtil.singleQuoteToDouble("{'city': 'SPb', 'state': 'Leningrad'}");

        Address fullAddress = AddressDeserializer.deserializeFull(json);
        assertThat(fullAddress.city, equalTo("SPb"));
        assertThat(fullAddress.state, equalTo("Leningrad"));

        Address emptyAddress = AddressDeserializer.deserializeEmpty(json);
        assertThat(emptyAddress.city, nullValue());
        assertThat(emptyAddress.state, nullValue());
    }

    private static class AddressDeserializer {
        private static final ObjectMapper fullObjectMapper;
        private static final ObjectMapper emptyObjectMapper;

        static {
            ObjectMapper mapper = new ObjectMapper();
            fullObjectMapper = mapper.copy().addMixIn(Address.class, FullAddressMixin.class);
            emptyObjectMapper = mapper.copy().addMixIn(Address.class, EmptyAddressMixin.class);
        }

        static Address deserializeFull(String json) throws IOException {
            return fullObjectMapper.readValue(json, Address.class);
        }

        static Address deserializeEmpty(String json) throws IOException {
            return emptyObjectMapper.readValue(json, Address.class);
        }
    }

    @SuppressWarnings("WeakerAccess")
    private static final class Address {
        public String city;
        public String state;
    }

    @SuppressWarnings("unused")
    private abstract class FullAddressMixin {
        public String city;
        public String state;
    }

    @SuppressWarnings("unused")
    private abstract class EmptyAddressMixin {
        @JsonIgnore
        public String city;

        @JsonIgnore
        public String state;
    }
}
