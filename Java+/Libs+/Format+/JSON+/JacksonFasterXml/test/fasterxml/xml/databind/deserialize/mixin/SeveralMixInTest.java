package fasterxml.xml.databind.deserialize.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use several mix-ins for different deserialization.
 */
class SeveralMixInTest {

    @Test
    void deserialize() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{'city': 'SPb', 'state': 'Leningrad'}");

        var fullAddress = AddressDeserializer.deserializeFull(json);
        assertThat(fullAddress.city).isEqualTo("SPb");
        assertThat(fullAddress.state).isEqualTo("Leningrad");

        var emptyAddress = AddressDeserializer.deserializeEmpty(json);
        assertThat(emptyAddress.city).isNull();
        assertThat(emptyAddress.state).isNull();
    }

    private static class AddressDeserializer {
        private static final ObjectMapper fullObjectMapper;
        private static final ObjectMapper emptyObjectMapper;

        static {
            var mapper = new ObjectMapper();
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
    private static abstract class FullAddressMixin {
        public String city;
        public String state;
    }

    @SuppressWarnings("unused")
    private static abstract class EmptyAddressMixin {
        @JsonIgnore
        public String city;

        @JsonIgnore
        public String state;
    }
}
