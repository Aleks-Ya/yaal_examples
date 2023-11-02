package moshi;

import com.squareup.moshi.Moshi;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class SerializeTest {
    @Test
    void serialize() {
        var person = new Person("John", 30);
        var moshi = new Moshi.Builder().build();
        var adapter = moshi.adapter(Person.class);
        var actJson = adapter.toJson(person);
        assertThatJson(actJson).isEqualTo("""
                {"name": "John", "age": 30}""");
    }

    public record Person(String name, Integer age) {
    }
}


