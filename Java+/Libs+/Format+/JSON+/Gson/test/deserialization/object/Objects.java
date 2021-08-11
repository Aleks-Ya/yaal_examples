package deserialization.object;

import com.google.gson.Gson;
import myobject.MyObject;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Парсинг JSON в Java-объект.
 */
class Objects {
    private static final Gson gson = new Gson();

    @Test
    void object() {
        var json = "{\"number\":4,\"text\":\"abc\",\"inner\":{\"now\":\"Jul 8, 2015 6:37:19 AM\",\"sum\":18}}";
        var obj = gson.fromJson(json, MyObject.class);
        assertEquals(obj.number, 4);
        assertEquals(obj.text, "abc");
        assertFalse(obj.excluded);
        assertEquals(obj.inner.now, new Date(1436326639000L));
        assertEquals((int) obj.inner.sum, 18);
    }

}
