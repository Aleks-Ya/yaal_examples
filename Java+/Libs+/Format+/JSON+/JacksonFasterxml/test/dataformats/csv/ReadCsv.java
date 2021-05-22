package dataformats.csv;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

class ReadCsv {

    @Test
    void readWithoutSchemaWithoutHeader() throws IOException {
        var mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        var csv = "John,30\nMary,25\n";
        MappingIterator<String[]> it = mapper.readerFor(String[].class).readValues(csv);
        var all = it.readAll();
        assertThat(all, contains(
                new String[]{"John", "30"},
                new String[]{"Mary", "25"}
        ));
    }

    @Test
    void readWithoutSchemaWithHeader() throws IOException {
        var mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        var schema = CsvSchema.emptySchema().withHeader();
        var csv = "name,age\nJohn,30\nMary,25\n";
        MappingIterator<Map<String, String>> it = mapper.readerFor(Map.class).with(schema).readValues(csv);
        var all = it.readAll();
        assertThat(all, contains(
                Map.of("name", "John", "age", "30"),
                Map.of("name", "Mary", "age", "25")
        ));
    }
}
