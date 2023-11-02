package csv.parse;

import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static csv.parse.HeaderTest.Headers.Age;
import static csv.parse.HeaderTest.Headers.Name;
import static csv.parse.HeaderTest.Headers.Title;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

class HeaderTest {
    private static final File file = ResourceUtil.resourceToFile(HeaderTest.class, "HeaderTest.csv");
    private static final String HEADER_1 = "Name";
    private static final String HEADER_2 = "Title";
    private static final String HEADER_3 = "Age";

    @Test
    void getHeaderNames() throws IOException {
        var format = CSVFormat.DEFAULT.builder().setHeader().build();
        try (var reader = new FileReader(file);
             var parser = format.parse(reader)) {
            var headerNames = parser.getHeaderNames();
            assertThat(headerNames).containsExactly(HEADER_1, HEADER_2, HEADER_3);
        }
    }

    @Test
    void getHeaderMap() throws IOException {
        var format = CSVFormat.DEFAULT.builder().setHeader().build();
        try (var reader = new FileReader(file);
             var parser = format.parse(reader)) {
            var headerMap = parser.getHeaderMap();
            assertThat(headerMap)
                    .containsEntry(HEADER_1, 0)
                    .containsEntry(HEADER_2, 1)
                    .containsEntry(HEADER_3, 2);
        }
    }

    @Test
    void byIndex() throws IOException {
        var format = CSVFormat.DEFAULT.builder().setHeader().build();
        try (var reader = new FileReader(file);
             var parser = format.parse(reader)) {
            assertThat(parser.getHeaderNames()).containsExactly(HEADER_1, HEADER_2, HEADER_3);
            var lines = parser.stream()
                    .map(record -> format("%s-%s-%s", record.get(0), record.get(1), record.get(2)))
                    .toList();
            assertThat(lines).containsExactly("John-Mr.-30", "Mary-Ms.-25");
        }
    }


    @Test
    void byName() throws IOException {
        var format = CSVFormat.DEFAULT.builder().setHeader().build();
        try (var reader = new FileReader(file);
             var parser = format.parse(reader)) {
            assertThat(parser.getHeaderNames()).containsExactly(HEADER_1, HEADER_2, HEADER_3);
            var lines = parser.stream()
                    .map(record -> format("%s-%s-%s", record.get(HEADER_1), record.get(HEADER_2), record.get(HEADER_3)))
                    .toList();
            assertThat(lines).containsExactly("John-Mr.-30", "Mary-Ms.-25");
        }
    }

    @Test
    void byEnum() throws IOException {
        var format = CSVFormat.DEFAULT.builder().setHeader(Headers.class).setSkipHeaderRecord(true).build();
        try (var reader = new FileReader(file);
             var parser = format.parse(reader)) {
            assertThat(parser.getHeaderNames()).containsExactly(HEADER_1, HEADER_2, HEADER_3);
            var lines = parser.stream()
                    .map(record -> format("%s-%s-%s", record.get(Name), record.get(Title), record.get(Age)))
                    .toList();
            assertThat(lines).containsExactly("John-Mr.-30", "Mary-Ms.-25");
        }
    }

    enum Headers {
        Name, Title, Age
    }
}