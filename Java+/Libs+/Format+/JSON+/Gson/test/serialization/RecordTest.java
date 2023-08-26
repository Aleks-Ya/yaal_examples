package serialization;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RecordTest {
    @Test
    void serialize() {
        var gson = new Gson();
        var expPerson = new Person("John", 30);
        var json = gson.toJson(expPerson);
        assertThat(json).isEqualTo("""
                {"name":"John","age":30}""");
        var actPerson = gson.fromJson(json, Person.class);
        assertThat(actPerson).isEqualTo(expPerson);
    }

    record Person(String name, Integer age) {
    }
}
