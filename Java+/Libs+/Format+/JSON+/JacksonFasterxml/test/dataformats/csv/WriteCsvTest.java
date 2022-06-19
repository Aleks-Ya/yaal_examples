package dataformats.csv;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class WriteCsvTest {

    @Test
    void writePojo() throws IOException {
        var mapper = new CsvMapper();
        var expValue = new PersonPojo();
        expValue.setName("John");
        expValue.setAge(30);
        var schema = mapper.schemaFor(PersonPojo.class);
        var csv = mapper.writer(schema).writeValueAsString(expValue);

        MappingIterator<PersonPojo> it = mapper.readerFor(PersonPojo.class).with(schema).readValues(csv);
        var all = it.readAll();
        assertThat(all).contains(expValue);
    }

    @SuppressWarnings("unused")
    private static class PersonPojo {
        private String name;
        private int age;

        private PersonPojo() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            var that = (PersonPojo) o;
            return age == that.age &&
                    Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }
}
