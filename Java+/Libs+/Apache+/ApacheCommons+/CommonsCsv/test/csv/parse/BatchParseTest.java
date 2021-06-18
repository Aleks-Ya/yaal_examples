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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class BatchParseTest {

    @Test
    void parseByBatches() throws IOException {
        var file = ResourceUtil.resourceToFile(BatchParseTest.class, "BatchParseTest.csv");

        var header1 = "Name";
        var header2 = "Title";
        var header3 = "Age";

        try (Reader reader = spy(new FileReader(file));
             var parser = CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
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

            var readNumber = 2;//Read headers + Read values (8192 bytes buffer in ExtendedBufferedReader)
            verify(reader, times(readNumber))
                    .read(any(char[].class), any(Integer.class), any(Integer.class));
        }
    }

}