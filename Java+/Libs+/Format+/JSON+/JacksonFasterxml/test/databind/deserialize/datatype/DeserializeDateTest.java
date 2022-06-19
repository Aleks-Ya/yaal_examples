package databind.deserialize.datatype;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Deserialize Java 8 dates.
 */
class DeserializeDateTest {
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void dates() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{" +
                "'localDate': '2017-01-15'," +
                "'localDateTime': '2007-12-03T10:15:30'" +
                "}");

        var names = mapper.readValue(json, Dates.class);

        assertThat(names.localDate).isEqualTo(LocalDate.of(2017, 1, 15));
        assertThat(names.localDateTime).isEqualTo(LocalDateTime.of(2007, 12, 3, 10, 15, 30));
    }

    @SuppressWarnings("WeakerAccess")
    private static class Dates {
        public LocalDate localDate;
        public LocalDateTime localDateTime;
    }

}
