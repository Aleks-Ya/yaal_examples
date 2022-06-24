package deserialization.object;

import com.google.gson.Gson;
import myobject.MyObject;
import org.junit.jupiter.api.Test;

import java.util.Date;

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
        assertThat(obj.inner.now).isEqualTo(new Date(1436326639000L));
        assertThat((int) obj.inner.sum).isEqualTo(18);
    }

}
