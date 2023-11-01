package csv.parse;

import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static org.assertj.core.api.Assertions.assertThat;

class ParseCsvTest {
    @Test
    void tabDelimiter() throws IOException {
        var file = ResourceUtil.resourceToFile(ParseCsvTest.class, "ParseTest_tabDelimiter.csv");

        var header1 = "Name";
        var header2 = "Title";
        var header3 = "Age";

        var delimiter = '\t';
        var format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setDelimiter(delimiter)
                .setRecordSeparator(System.lineSeparator())
                .build();
        try (Reader reader = new FileReader(file);
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