package fasterxml.yaml.databind;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.junit.jupiter.api.Test;

import static fasterxml.yaml.databind.ParseDataBindTest.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;


class ParseDataBindTest {

    @Test
    void name() throws Exception {
        var yaml = """
                name: John
                age: 30
                gender: MALE
                """;
        var mapper = new YAMLMapper();
        var person = mapper.readValue(yaml, Person.class);

        assertThat(person.getName()).isEqualTo("John");
        assertThat(person.getAge()).isEqualTo(30);
        assertThat(person.getGender()).isEqualTo(MALE);
    }

    private static class Person {
        private String name;
        private Integer age;
        private Gender gender;

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

        private Gender getGender() {
            return gender;
        }

        @SuppressWarnings("unused")
        public void setGender(Gender gender) {
            this.gender = gender;
        }
    }

    enum Gender {
        MALE, FEMALE
    }
}
