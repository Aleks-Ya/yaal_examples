package fasterxml.yaml.databind;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ParseDataBindTest {

    @Test
    void name() throws Exception {
        var yaml = "name: John\n" +
                "age: 30";
        var mapper = new YAMLMapper();
        var person = mapper.readValue(yaml, Person.class);

        assertThat(person.getName()).isEqualTo("John");
        assertThat(person.getAge()).isEqualTo(30);
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
