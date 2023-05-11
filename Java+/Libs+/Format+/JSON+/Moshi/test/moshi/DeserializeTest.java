package moshi;

import com.squareup.moshi.Moshi;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class DeserializeTest {
    @Test
    void deserialize() throws IOException {
        var json = """
                {"name": "John", "age": 30}""";
        var moshi = new Moshi.Builder().build();
        var adapter = moshi.adapter(Person.class);
        var person = adapter.fromJson(json);
        assertThat(person).isEqualTo(new Person("John", 30));
    }

    public record Person(String name, Integer age) {
    }
}


