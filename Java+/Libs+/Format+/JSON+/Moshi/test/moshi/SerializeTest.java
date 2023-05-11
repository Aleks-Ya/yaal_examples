package moshi;

import com.squareup.moshi.Moshi;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;

class SerializeTest {
    @Test
    void serialize() {
        var person = new Person("John", 30);
        var moshi = new Moshi.Builder().build();
        var adapter = moshi.adapter(Person.class);
        var actJson = adapter.toJson(person);
        var expJson = """
                {"name": "John", "age": 30}""";
        JsonAssert.assertJsonEquals(expJson, actJson);
    }

    public record Person(String name, Integer age) {
    }
}


