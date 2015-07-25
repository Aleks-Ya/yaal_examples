package deserialization;

import com.google.gson.Gson;
import myobject.MyObject;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.arrayWithSize;
import static org.junit.Assert.*;

/**
 * Парсинг JSON в Java-объект.
 */
public class Array {
    private static final Gson gson = new Gson();

    @Test
    public void arrayPrimitive() {
        assertArrayEquals(new String[]{"abc"}, gson.fromJson("[\"abc\"]", String[].class));
    }

    @Test
    public void arrayObject() {
        String json = "[" +
                "{\"number\":4,\"text\":\"abc\",\"inner\":{\"now\":\"Jul 8, 2015 6:37:19 AM\",\"sum\":18}}," +
                "{\"number\":1,\"text\":\"xyz\",\"inner\":{\"now\":\"Jul 15, 2014 9:37:19 AM\",\"sum\":200}}" +
                "]";
        MyObject[] objects = gson.fromJson(json, MyObject[].class);
        assertThat(objects, arrayWithSize(2));
        MyObject act1 = objects[0];
        MyObject act2 = objects[1];
        assertThat(act1.inner.sum, Matchers.equalTo(18));
        assertThat(act2.inner.sum, Matchers.equalTo(200));
    }

}
