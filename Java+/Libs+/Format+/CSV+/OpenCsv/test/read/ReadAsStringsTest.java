package read;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class ReadAsStringsTest {
    @Test
    void readNextLine() throws IOException, CsvValidationException {
        var csv = ResourceUtil.resourceToFile(getClass(), "data.csv");
        var lines = new ArrayList<String>();
        try (var reader = new CSVReaderBuilder(new FileReader(csv)).build()) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                var id = nextLine[0];
                var name = nextLine[1];
                var gender = nextLine[2];
                var line = String.format("%s %s %s", id, name, gender);
                lines.add(line);
            }
        }
        assertThat(lines).containsExactly("id name gender", "1 John M", "2 Mary F", "3 Mark M");
    }

    @Test
    void readAllLines() throws IOException, CsvException {
        var csv = ResourceUtil.resourceToFile(getClass(), "data.csv");
        try (var reader = new CSVReaderBuilder(new FileReader(csv)).build()) {
            var lines = reader.readAll().stream().map(entry -> {
                var id = entry[0];
                var name = entry[1];
                var gender = entry[2];
                return String.format("%s %s %s", id, name, gender);
            }).toList();
            assertThat(lines).containsExactly("id name gender", "1 John M", "2 Mary F", "3 Mark M");
        }
    }
}
