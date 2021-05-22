package databind.deserialize.mixin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;

/**
 * Source: https://dzone.com/articles/jackson-mixin-to-the-rescue
 */
public class JacksonMixInTest {

    private static ObjectMapper buildMapper() {
        var mapper = new ObjectMapper();
        mapper.setVisibility(mapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        return mapper;
    }

    @Test
    public void deserialize() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{'city': 'SPb', 'state': 'Leningrad'}");
        System.out.println(json);
        var mapper = buildMapper();
        mapper.addMixIn(Address.class, AddressMixin.class);
        final var deserializedUser = mapper.readValue(json, Address.class);
        System.out.println(deserializedUser);
    }

    private static class Address {
        private final String city;
        private final String state;

        Address(String city, String state) {
            this.city = city;
            this.state = state;
        }

        @Override
        public String toString() {
            return "Address [city=" + city + ", state=" + state + "]";
        }
    }

    private static abstract class AddressMixin {

        @JsonCreator
        @SuppressWarnings("unused")
        public AddressMixin(
                @JsonProperty("city") String city,
                @JsonProperty("state") String state) {
            System.out.println("Wont be called");
        }

    }
}
