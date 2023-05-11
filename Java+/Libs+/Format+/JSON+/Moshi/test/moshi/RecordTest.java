package moshi;

import com.squareup.moshi.Moshi;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Serialize and deserialize a Java 16 Record.
 */
class RecordTest {
    @Test
    void record() throws IOException {
        var expPerson = new Person("John", 30);
        var moshi = new Moshi.Builder().build();
        var adapter = moshi.adapter(Person.class);
        var json = adapter.toJson(expPerson);
        var actPerson = adapter.fromJson(json);
        assertThat(actPerson).isEqualTo(expPerson);
    }

    public record Person(String name, Integer age) {
    }
}


