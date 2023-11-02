package deserialization.object;

import com.google.gson.Gson;
import myobject.MyObject;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static java.time.LocalDateTime.parse;
import static java.time.ZoneId.systemDefault;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Парсинг JSON в Java-объект.
 */
class ObjectTest {
    private static final Gson gson = new Gson();

    @Test
    void object() {
        var json = "{\"number\":4,\"text\":\"abc\",\"inner\":{\"now\":\"Jul 8, 2015 6:37:19 AM\",\"sum\":18}}";
        var obj = gson.fromJson(json, MyObject.class);
        assertThat(obj.number).isEqualTo(4);
        assertThat(obj.text).isEqualTo("abc");
        assertThat(obj.excluded).isFalse();
        var expDate = Date.from(parse("2015-07-08T06:37:19").atZone(systemDefault()).toInstant());
        assertThat(obj.inner.now).isEqualTo(expDate);
        assertThat((int) obj.inner.sum).isEqualTo(18);
    }

}
