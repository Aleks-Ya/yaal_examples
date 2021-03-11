package yaml.databind;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ParseDataBindTest {

    @Test
    public void name() throws Exception {
        var yaml = "name: John\n" +
                "age: 30";
        var mapper = new ObjectMapper(new YAMLFactory());
        var person = mapper.readValue(yaml, Person.class);

        assertThat(person.getName(), equalTo("John"));
        assertThat(person.getAge(), equalTo(30));
    }

    private static class Person {
        private String name;
        private Integer age;

        String getName() {
            return name;
        }

        @SuppressWarnings("unused")
        void setName(String name) {
            this.name = name;
        }

        Integer getAge() {
            return age;
        }

        @SuppressWarnings("unused")
        void setAge(Integer age) {
            this.age = age;
        }
    }
}
