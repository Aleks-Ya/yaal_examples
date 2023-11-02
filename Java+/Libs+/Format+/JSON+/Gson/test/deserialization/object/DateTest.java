package deserialization.object;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static java.time.LocalDateTime.parse;
import static java.time.ZoneId.systemDefault;
import static org.assertj.core.api.Assertions.assertThat;

class DateTest {
    private static final Gson gson = new Gson();

    @Test
    void dateDefault() {
        var json = "\"Jul 8, 2015 6:37:19 AM\"";
        var expDate = Date.from(parse("2015-07-08T06:37:19").atZone(systemDefault()).toInstant());
        var actDate = gson.fromJson(json, Date.class);
        assertThat(actDate).isEqualTo(expDate);
    }

    @Test
    void dateFormatted() {
        var gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        var json = "\"2015-07-08 06:37:19\"";
        var expDate = Date.from(parse("2015-07-08T06:37:19").atZone(systemDefault()).toInstant());
        assertThat(gson.fromJson(json, Date.class)).isEqualTo(expDate);
    }
}
