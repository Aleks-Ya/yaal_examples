package serialization;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PrettyPrintingTest {
    @Test
    void serialize() {
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var expPerson = new Person("John", 30);
        var json = gson.toJson(expPerson);
        assertThat(json).isEqualTo("""
                {
                  "name": "John",
                  "age": 30
                }""");
        var actPerson = gson.fromJson(json, Person.class);
        assertThat(actPerson).isEqualTo(expPerson);
    }

    record Person(String name, Integer age) {
    }
}
