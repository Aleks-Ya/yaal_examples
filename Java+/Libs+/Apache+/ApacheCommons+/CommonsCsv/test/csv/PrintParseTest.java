package csv;

import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import static org.assertj.core.api.Assertions.assertThat;

class PrintParseTest {

    @Test
    void printAndParse() throws IOException {
        var file = File.createTempFile(PrintParseTest.class.getSimpleName(), ".csv");
        System.out.println("CSV file: " + file.getAbsolutePath());

        var header1 = "H1";
        var header2 = "H2";

        var value1 = "v1";
        var value2 = "v2";
        var value3 = "v3,v33";
        var value4 = "v4";
        var value5 = "v5\tv55";
        var value6 = "v6";
        var value7 = "v7\nv77";
        var value8 = "v8";

        try (var fileWriter = new FileWriter(file);
             var printer = CSVFormat.DEFAULT
                     .withHeader(header1, header2)
                     .withSystemRecordSeparator()
                     .print(fileWriter)) {
            printer.printRecord(value1, value2);
            printer.printRecord(value3, value4);
            printer.printRecord(value5, value6);
            printer.printRecord(value7, value8);
        }

        try (Reader reader = new FileReader(file);
             var parser = CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withSystemRecordSeparator()
                     .parse(reader)) {

            var headerNames = parser.getHeaderNames();
            assertThat(headerNames).containsExactly(header1, header2);

            var records = parser.getRecords();
            assertThat(records).hasSize(4);

            var record0 = records.get(0);
            assertThat(record0.get(header1)).isEqualTo(value1);
            assertThat(record0.get(header2)).isEqualTo(value2);

            var record1 = records.get(1);
            assertThat(record1.get(header1)).isEqualTo(value3);
            assertThat(record1.get(header2)).isEqualTo(value4);

            var record2 = records.get(2);
            assertThat(record2.get(header1)).isEqualTo(value5);
            assertThat(record2.get(header2)).isEqualTo(value6);

            var record3 = records.get(3);
            assertThat(record3.get(header1)).isEqualTo(value7);
            assertThat(record3.get(header2)).isEqualTo(value8);
        }
    }

}