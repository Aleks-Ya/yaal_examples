package csv.parse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.FileReader;
import java.io.IOException;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

class ParseCsvTest {
    @Test
    void commaDelimiter_iterable() throws IOException {
        var file = ResourceUtil.resourceToFile(ParseCsvTest.class, "ParseTest_commaDelimiter.csv");

        var header1 = "Name";
        var header2 = "Title";
        var header3 = "Age";

        var format = CSVFormat.DEFAULT.builder().setHeader().build();
        try (var reader = new FileReader(file);
             var parser = format.parse(reader)) {

            var headerNames = parser.getHeaderNames();
            assertThat(headerNames).containsExactly(header1, header2, header3);

            var sb = new StringBuilder();
            for (var record : parser) {
                sb
                        .append(record.get(header1))
                        .append("-")
                        .append(record.get(header2))
                        .append("-")
                        .append(record.get(header3))
                        .append("\n");
            }
            assertThat(sb.toString()).isEqualTo("John-Mr.-30\nMary-Ms.-25\nMark-Mr.-20\nGary-Mr.-15\n");
        }
    }

    @Test
    void commaDelimiter_stream() throws IOException {
        var file = ResourceUtil.resourceToFile(ParseCsvTest.class, "ParseTest_commaDelimiter.csv");

        var header1 = "Name";
        var header2 = "Title";
        var header3 = "Age";

        var format = CSVFormat.DEFAULT.builder().setHeader().build();
        try (var reader = new FileReader(file);
             var parser = format.parse(reader)) {

            var headerNames = parser.getHeaderNames();
            assertThat(headerNames).containsExactly(header1, header2, header3);

            var lines = parser.stream()
                    .map(record -> format("%s-%s-%s", record.get(header1), record.get(header2), record.get(header3)))
                    .toList();
            assertThat(lines).containsExactly("John-Mr.-30", "Mary-Ms.-25", "Mark-Mr.-20", "Gary-Mr.-15");
        }
    }

    @Test
    void commaDelimiter_getRecords() throws IOException {
        var file = ResourceUtil.resourceToFile(ParseCsvTest.class, "ParseTest_commaDelimiter.csv");

        var header1 = "Name";
        var header2 = "Title";
        var header3 = "Age";

        var format = CSVFormat.DEFAULT.builder().setHeader().build();
        try (var reader = new FileReader(file);
             var parser = format.parse(reader)) {

            var headerNames = parser.getHeaderNames();
            assertThat(headerNames).containsExactly(header1, header2, header3);

            var records = parser.getRecords();
            assertThat(records).map(CSVRecord::toString).containsExactly(
                    "CSVRecord [comment='null', recordNumber=1, values=[John, Mr., 30]]",
                    "CSVRecord [comment='null', recordNumber=2, values=[Mary, Ms., 25]]",
                    "CSVRecord [comment='null', recordNumber=3, values=[Mark, Mr., 20]]",
                    "CSVRecord [comment='null', recordNumber=4, values=[Gary, Mr., 15]]");
        }
    }


    @Test
    void tabDelimiter() throws IOException {
        var file = ResourceUtil.resourceToFile(ParseCsvTest.class, "ParseTest_tabDelimiter.csv");

        var header1 = "Name";
        var header2 = "Title";
        var header3 = "Age";

        var delimiter = '\t';
        var format = CSVFormat.DEFAULT.builder().setHeader().setDelimiter(delimiter).build();
        try (var reader = new FileReader(file);
             var parser = format.parse(reader)) {

            var headerNames = parser.getHeaderNames();
            assertThat(headerNames).containsExactly(header1, header2, header3);

            var sb = new StringBuilder();
            for (var record : parser) {
                sb
                        .append(record.get(header1))
                        .append("-")
                        .append(record.get(header2))
                        .append("-")
                        .append(record.get(header3))
                        .append("\n");
            }
            assertThat(sb.toString()).isEqualTo("John-Mr.-30\nMary-Ms.-25\nMark-Mr.-20\nGary-Mr.-15\n");
        }
    }

}