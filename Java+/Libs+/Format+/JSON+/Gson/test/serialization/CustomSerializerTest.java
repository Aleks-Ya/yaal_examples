package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomSerializerTest {
    private final JsonSerializer<Person> serializer = (person, typeOfSrc, context) ->
            new JsonPrimitive(person.name() + "-" + person.age());
    private final Gson gson = new GsonBuilder().registerTypeAdapter(Person.class, serializer).create();

    @Test
    void serialize() {
        var person = new Person("John", 30);
        var json = gson.toJson(person);
        assertThat(json).isEqualTo("""
                "John-30\"""");
    }

    @Test
    void serializeNested() {
        var person = new Person("John", 30);
        var department = new Department("Sales", person);
        var json = gson.toJson(department);
        assertThat(json).isEqualTo("""
                {"name":"Sales","head":"John-30"}""");
    }

    record Person(String name, Integer age) {
    }

    record Department(String name, Person head) {
    }
}
