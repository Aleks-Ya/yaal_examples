package deserialization.object;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Парсинг JSON в Java-объект.
 */
class PrimitiveTest {
    private static final Gson gson = new Gson();

    @Test
    void primitive() {
        assertThat(gson.fromJson("1", int.class)).isEqualTo(1);
        assertThat(gson.fromJson("1", Integer.class)).isEqualTo(1);
        assertThat(gson.fromJson("1", Long.class)).isEqualTo(1L);
        assertThat(gson.fromJson("false", Boolean.class)).isEqualTo(false);
        assertThat(gson.fromJson("\"abc\"", String.class)).isEqualTo("abc");
    }

}
