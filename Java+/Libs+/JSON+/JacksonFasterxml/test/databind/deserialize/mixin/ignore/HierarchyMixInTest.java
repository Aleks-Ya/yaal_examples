package databind.deserialize.mixin.ignore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Mix-In class is an interface.
 */
public class HierarchyMixInTest {

    @Test
    public void deserialize() throws IOException {
        Address address = new Address();
        address.setId("1");
        address.setCity("SPb");
        address.setState("Leningrad");

        ObjectMapper mapper = new ObjectMapper();
        String fullJson = mapper.writeValueAsString(address);
        assertThat(fullJson, containsString("id"));
        assertThat(fullJson, containsString("city"));
        assertThat(fullJson, containsString("state"));

        Address fullAddress = mapper.readValue(fullJson, Address.class);
        assertThat(fullAddress.getId(), equalTo("1"));
        assertThat(fullAddress.getCity(), equalTo("SPb"));
        assertThat(fullAddress.getState(), equalTo("Leningrad"));

        mapper = new ObjectMapper(); //reset DeserializerCache
        mapper.addMixIn(Address.class, AddressMixin.class);

        Address mixedInAddress = mapper.readValue(fullJson, Address.class);
        assertThat(mixedInAddress.getId(), nullValue());
        assertThat(mixedInAddress.getCity(), nullValue());
        assertThat(mixedInAddress.getState(), nullValue());
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

        public String getState() {
            return state;
        }

        void setState(String state) {
            this.state = state;
        }
    }
}
