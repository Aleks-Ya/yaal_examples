package fasterxml.csv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateCsvSchemaTest {

    @Test
    void fromPojo() {
        var mapper = new CsvMapper();
        var schema = mapper.schemaFor(PersonPojo.class);
        assertThat(schema).hasToString(
                "[CsvSchema: columns=[\"age\"/STRING,\"name\"/STRING], header? false, skipFirst? false, comments? false, any-properties? N/A]");
    }

    @Test
    void withBuilder() {
        var schema = CsvSchema.builder()
                .addColumn("name")
                .addColumn("age", CsvSchema.ColumnType.NUMBER)
                .build();
        assertThat(schema).hasToString(
                "[CsvSchema: columns=[\"name\"/STRING,\"age\"/NUMBER], header? false, skipFirst? false, comments? false, any-properties? N/A]");
    }

    @Test
    void fromFirstLine() throws JsonProcessingException {
        var csv = "name,age\nJohn,30\n";
        var bootstrapSchema = CsvSchema.emptySchema().withHeader();
        var mapper = new CsvMapper();
        var person = (PersonPojo) mapper.readerFor(PersonPojo.class).with(bootstrapSchema).readValue(csv);
        assertThat(person.getName()).isEqualTo("John");
        assertThat(person.getAge()).isEqualTo(30);
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
    }
}
