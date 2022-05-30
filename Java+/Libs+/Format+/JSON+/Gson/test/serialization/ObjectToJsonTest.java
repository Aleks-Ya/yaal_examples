package serialization;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Представление Java-объекта в виде JSON.
 */
class ObjectToJsonTest {
    /**
     * Компактный формат JSON.
     */
    @Test
    void compact() {
        var builder = new GsonBuilder();
        var gson = builder.create();

        Object obj = new MyObject();

        var jsonStr = gson.toJson(obj);
        assertEquals("{\"number\":4,\"text\":\"abc\",\"inner\":{\"now\":\"Jul 8, 2015, 6:37:19 AM\",\"sum\":18}}",
                jsonStr);
        System.out.println(jsonStr);
    }

    /**
     * Читаемый формат JSON.
     */
    @Test
    void prettyPrint() {
        var gson = new GsonBuilder().setPrettyPrinting().create();
        Object obj = new MyObject();
        var jsonStr = gson.toJson(obj);
        assertEquals("{\n" +
                "  \"number\": 4,\n" +
                "  \"text\": \"abc\",\n" +
                "  \"inner\": {\n" +
                "    \"now\": \"Jul 8, 2015, 6:37:19 AM\",\n" +
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

    static class Inner {
        Date now = new Date(1436326639819L);
        Integer sum = 18;
    }
}
