package deserialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import myobject.MyObject;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Парсинг JSON в Java-объект.
 */
public class Objects {
    private static final Gson gson = new Gson();

    @Test
    public void object() {
        String json = "{\"number\":4,\"text\":\"abc\",\"inner\":{\"now\":\"Jul 8, 2015 6:37:19 AM\",\"sum\":18}}";
        MyObject obj = gson.fromJson(json, MyObject.class);
        assertEquals(obj.number, 4);
        assertEquals(obj.text, "abc");
        assertEquals(obj.excluded, false);
        assertEquals(obj.inner.now, new Date(1436326639000L));
        assertEquals((int) obj.inner.sum, 18);
    }

}
