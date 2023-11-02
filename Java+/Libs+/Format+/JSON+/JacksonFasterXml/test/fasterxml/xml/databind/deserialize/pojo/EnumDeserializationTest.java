package fasterxml.xml.databind.deserialize.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static util.JsonUtil.singleQuoteToDouble;

/**
 * Deserialize enum POJO field.
 */
class EnumDeserializationTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void readEnum() throws IOException {
        var json = singleQuoteToDouble("{'name': 'John', 'status': 'SINGLE'}");
        var user = mapper.readValue(json, User.class);
        assertThat(user.getName()).isEqualTo("John");
        assertThat(user.getStatus()).isEqualTo(Status.SINGLE);
    }

    @Test
    void enumCaseInsensitiveFail() {
        assertThatThrownBy(() -> {
            var json = singleQuoteToDouble("{'name': 'John', 'status': 'single'}");
            mapper.readValue(json, User.class);
        }).isInstanceOf(InvalidFormatException.class);
    }

    @Test
    void enumCaseInsensitiveSuccess() throws IOException {
        var json = singleQuoteToDouble("{'name': 'John', 'status': 'single'}");
        var mapper = JsonMapper.builder().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS).build();
        var user = mapper.readValue(json, User.class);
        assertThat(user.getName()).isEqualTo("John");
        assertThat(user.getStatus()).isEqualTo(Status.SINGLE);
    }

    @Test
    void enumCaseInsensitiveCreator() throws IOException {
        var json = singleQuoteToDouble("{'name': 'John', 'status': 'single'}");
        var user = mapper.readValue(json, UserWithCreator.class);
        assertThat(user.getName()).isEqualTo("John");
        assertThat(user.getStatus()).isEqualTo(StatusWithCreator.SINGLE);
    }

    private enum Status {
        SINGLE, MARRIED, DIVORCED
    }

    private enum StatusWithCreator {
        SINGLE, MARRIED, DIVORCED;

        @JsonCreator
        public static StatusWithCreator fromString(String key) {
            return key == null ? null : StatusWithCreator.valueOf(key.toUpperCase());
        }
    }

    @SuppressWarnings("unused")
    private static class User {
        private String name;

        private Status status;

        public String getName() {
            return name;
        }

        public Status getStatus() {
            return status;
        }
    }

    private static class UserWithCreator {
        private String name;

        private StatusWithCreator status;

        public String getName() {
            return name;
        }

        public StatusWithCreator getStatus() {
            return status;
        }
    }

}
