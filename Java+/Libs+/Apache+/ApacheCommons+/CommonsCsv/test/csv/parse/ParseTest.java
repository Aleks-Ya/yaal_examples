package csv.parse;

import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

class ParseTest {

    @Test
    void tabDelimiter() throws IOException {
        var file = ResourceUtil.resourceToFile(ParseTest.class, "ParseTest_tabDelimiter.csv");

        var header1 = "Name";
        var header2 = "Title";
        var header3 = "Age";

        var delimiter = '\t';
        try (Reader reader = new FileReader(file);
             var parser = CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withDelimiter(delimiter)
                     .withSystemRecordSeparator()
                     .parse(reader)) {

            var headerNames = parser.getHeaderNames();
            assertThat(headerNames, contains(header1, header2, header3));

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
            assertThat(sb.toString(), equalTo("John-Mr.-30\nMary-Ms.-25\nMark-Mr.-20\nGary-Mr.-15\n"));
        }
    }

}