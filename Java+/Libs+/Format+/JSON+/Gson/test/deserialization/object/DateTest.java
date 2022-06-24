package deserialization.object;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Парсинг JSON в Java-объект.
 */
class DateTest {
    private static final Gson gson = new Gson();

    @Test
    void dateDefault() {
        var json = "\"Jul 8, 2015 6:37:19 AM\"";
        assertThat(gson.fromJson(json, Date.class)).isEqualTo(new Date(1436326639000L));
    }

    @Test
    void dateFormatted() {
        var gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        var json = "\"2015-07-08 06:37:19\"";
        assertThat(gson.fromJson(json, Date.class)).isEqualTo(new Date(1436326639000L));
    }
}
