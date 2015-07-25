package deserialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import myobject.MyObject;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Парсинг JSON в Java-объект.
 */
public class Primitive {
    private static final Gson gson = new Gson();

    @Test
    public void primitive() {
        assertThat(gson.fromJson("1", int.class), equalTo(1));
        assertThat(gson.fromJson("1", Integer.class), equalTo(1));
        assertThat(gson.fromJson("1", Long.class), equalTo(1L));
        assertThat(gson.fromJson("false", Boolean.class), equalTo(false));
        assertThat(gson.fromJson("\"abc\"", String.class), equalTo("abc"));
    }

}
