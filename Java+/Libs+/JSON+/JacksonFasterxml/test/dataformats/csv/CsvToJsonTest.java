package dataformats.csv;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class CsvToJsonTest {

    @Test
    public void csvToJson() throws IOException {
        var csv = "name,age\nJohn,30\nMary,25\n";
        var csvSchema = CsvSchema.emptySchema().withHeader();
        var csvMapper = new CsvMapper();
        MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader().forType(Map.class).with(csvSchema).readValues(csv);
        var list = mappingIterator.readAll();

        var baos = new ByteArrayOutputStream();
        var objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(baos, list);
        var actJson = baos.toString();
        var expJson = "[{'name':'John','age':'30'},{'name':'Mary','age':'25'}]";
        JsonAssert.assertJsonEquals(expJson, actJson);
    }
}