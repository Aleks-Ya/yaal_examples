package deserialization.object;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomDeserializerTest {
    private final JsonDeserializer<Person> deserializer = (json, typeOfT, context) -> {
        var str = json.getAsString();
        var split = str.split("-");
        var name = split[0];
        var age = Integer.valueOf(split[1]);
        return new Person(name, age);
    };

    private final Gson gson = new GsonBuilder().registerTypeAdapter(Person.class, deserializer).create();

    @Test
    void deserialize() {
        var json = """
                "John-30\"""";
        var person = gson.fromJson(json, Person.class);
        assertThat(person).isEqualTo(new Person("John", 30));
    }

    @Test
    void deserializeNested() {
        var json = """
                {"name":"Sales","head":"John-30"}""";
        var department = gson.fromJson(json, Department.class);
        assertThat(department).isEqualTo(new Department("Sales", new Person("John", 30)));
    }

    record Person(String name, Integer age) {
    }

    record Department(String name, Person head) {
    }
}
