package fasterxml.xml.databind.deserialize.mixin.ignore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mix-In class is an interface.
 */
class HierarchyMixInTest {

    @Test
    void deserialize() throws IOException {
        var fullJson = JsonUtil.singleQuoteToDouble("{'id': '1', 'city': 'SPb', 'state': 'Leningrad'}");

        var mapper = new ObjectMapper();
        assertThat(fullJson).containsSubsequence("id", "city", "state");

        var fullAddress = mapper.readValue(fullJson, Address.class);
        assertThat(fullAddress.getId()).isEqualTo("1");
        assertThat(fullAddress.getCity()).isEqualTo("SPb");
        assertThat(fullAddress.getState()).isEqualTo("Leningrad");

        mapper = new ObjectMapper(); //reset DeserializerCache
        mapper.addMixIn(Address.class, AddressMixin.class);

        var mixedInAddress = mapper.readValue(fullJson, Address.class);
        assertThat(mixedInAddress.getId()).isNull();
        assertThat(mixedInAddress.getCity()).isNull();
        assertThat(mixedInAddress.getState()).isNull();
    }

    @SuppressWarnings("unused")
    private interface Root {
        String getId();
    }


    private interface City extends Root {
        String getCity();
    }

    @SuppressWarnings("unused")
    private interface AddressMixin {
        @JsonIgnore
        String getId();

        @JsonIgnore
        String getCity();

        @JsonIgnore
        String getState();
    }

    @SuppressWarnings("unused")
    private static final class Address implements City {
        private String id;
        private String city;
        private String state;

        @Override
        public String getId() {
            return id;
        }

        void setId(String id) {
            this.id = id;
        }

        @Override
        public String getCity() {
            return city;
        }

        void setCity(String city) {
            this.city = city;
        }

        String getState() {
            return state;
        }

        void setState(String state) {
            this.state = state;
        }
    }
}
