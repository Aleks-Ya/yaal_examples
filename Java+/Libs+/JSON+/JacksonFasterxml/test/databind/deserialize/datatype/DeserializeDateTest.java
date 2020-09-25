package databind.deserialize.datatype;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import util.JsonUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Deserialize Java 8 dates.
 */
public class DeserializeDateTest {
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    public void dates() throws IOException {
        String json = JsonUtil.singleQuoteToDouble("{" +
                "'localDate': '2017-01-15'," +
                "'localDateTime': '2007-12-03T10:15:30'" +
                "}");

        Dates names = mapper.readValue(json, Dates.class);

        assertThat(names.localDate, equalTo(LocalDate.of(2017, 1, 15)));
        assertThat(names.localDateTime, equalTo(LocalDateTime.of(2007, 12, 03, 10, 15, 30)));
    }

    @SuppressWarnings("WeakerAccess")
    private static class Dates {
        public LocalDate localDate;
        public LocalDateTime localDateTime;
    }

}
