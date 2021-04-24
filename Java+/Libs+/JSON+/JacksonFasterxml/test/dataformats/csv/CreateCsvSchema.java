package dataformats.csv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class CreateCsvSchema {

    @Test
    void fromPojo() {
        var mapper = new CsvMapper();
        var schema = mapper.schemaFor(PersonPojo.class);
        assertThat(schema.toString(), equalTo(
                "[CsvSchema: columns=[\"age\"/STRING,\"name\"/STRING], header? false, skipFirst? false, comments? false, any-properties? N/A]"));
    }

    @Test
    void withBuilder() {
        var schema = CsvSchema.builder()
                .addColumn("name")
                .addColumn("age", CsvSchema.ColumnType.NUMBER)
                .build();
        assertThat(schema.toString(), equalTo(
                "[CsvSchema: columns=[\"name\"/STRING,\"age\"/NUMBER], header? false, skipFirst? false, comments? false, any-properties? N/A]"));
    }

    @Test
    void fromFirstLine() throws JsonProcessingException {
        var csv = "name,age\nJohn,30\n";
        var bootstrapSchema = CsvSchema.emptySchema().withHeader();
        var mapper = new CsvMapper();
        var person = (PersonPojo) mapper.readerFor(PersonPojo.class).with(bootstrapSchema).readValue(csv);
        assertThat(person.getName(), equalTo("John"));
        assertThat(person.getAge(), equalTo(30));
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
