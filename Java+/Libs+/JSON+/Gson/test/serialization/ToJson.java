package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

/**
 * Представление Java-объекта в виде JSON.
 */
public class ToJson {
    /**
     * Компактный формат JSON.
     */
    @Test
    public void compact() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Object obj = new MyObject();

        String jsonStr = gson.toJson(obj);
        assertEquals("{\"number\":4,\"text\":\"abc\",\"inner\":{\"now\":\"Jul 8, 2015 6:37:19 AM\",\"sum\":18}}",
                jsonStr);
        System.out.println(jsonStr);
    }

    /**
     * Читаемый формат JSON.
     */
    @Test
    public void prettyPrint() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Object obj = new MyObject();
        String jsonStr = gson.toJson(obj);
        assertEquals("{\n" +
                "  \"number\": 4,\n" +
                "  \"text\": \"abc\",\n" +
                "  \"inner\": {\n" +
                "    \"now\": \"Jul 8, 2015 6:37:19 AM\",\n" +
                "    \"sum\": 18\n" +
                "  }\n" +
                "}", jsonStr);
        System.out.println(jsonStr);
    }

    class MyObject {
        int number = 4;
        String text = "abc";
        transient boolean excluded = true;
        Inner inner = new Inner();
    }

    class Inner {
        Date now = new Date(1436326639819L);
        Integer sum = 18;
    }
}
