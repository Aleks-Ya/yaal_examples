package csv.parse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import util.ResourceUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BatchParseTest {

    @Test
    public void parseByBatches() throws IOException {
        File file = ResourceUtil.resourceToFile(BatchParseTest.class, "BatchParseTest.csv");

        String header1 = "Name";
        String header2 = "Title";
        String header3 = "Age";

        try (Reader reader = spy(new FileReader(file));
             CSVParser parser = CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withSystemRecordSeparator()
                     .parse(reader)) {

            List<String> headerNames = parser.getHeaderNames();
            assertThat(headerNames, contains(header1, header2, header3));

            StringBuilder sb = new StringBuilder();
            for (CSVRecord record : parser) {
                sb
                        .append(record.get(header1))
                        .append("-")
                        .append(record.get(header2))
                        .append("-")
                        .append(record.get(header3))
                        .append("\n");
            }
            assertThat(sb.toString(), equalTo("John-Mr.-30\nMary-Ms.-25\nMark-Mr.-20\nGary-Mr.-15\n"));

            int readNumber = 2;//Read headers + Read values (8192 bytes buffer in ExtendedBufferedReader)
            //noinspection ResultOfMethodCallIgnored
            verify(reader, times(readNumber))
                    .read(any(char[].class), any(Integer.class), any(Integer.class));
        }
    }

}