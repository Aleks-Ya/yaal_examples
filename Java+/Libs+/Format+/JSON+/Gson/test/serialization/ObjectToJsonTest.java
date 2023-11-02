package serialization;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static java.time.LocalDateTime.parse;
import static java.time.ZoneId.systemDefault;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Представление Java-объекта в виде JSON.
 */
@SuppressWarnings("unused")
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
        assertThat(jsonStr).isEqualTo(
                "{\"number\":4,\"text\":\"abc\",\"inner\":{\"now\":\"Jul 8, 2015, 6:37:19 AM\",\"sum\":18}}");
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
        assertThat(jsonStr).isEqualTo(
                """
                        {
                          "number": 4,
                          "text": "abc",
                          "inner": {
                            "now": "Jul 8, 2015, 6:37:19 AM",
                            "sum": 18
                          }
                        }"""
        );
        System.out.println(jsonStr);
    }

    static class Inner {
        Date now = Date.from(parse("2015-07-08T06:37:19").atZone(systemDefault()).toInstant());
        Integer sum = 18;
    }

    static class MyObject {
        int number = 4;
        String text = "abc";
        transient boolean excluded = true;
        Inner inner = new Inner();
    }
}
