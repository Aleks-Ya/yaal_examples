package csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class PrintParseTest {

    @Test
    public void printAndParse() throws IOException {
        File file = File.createTempFile(PrintParseTest.class.getSimpleName(), ".csv");
        System.out.println("CSV file: " + file.getAbsolutePath());

        String header1 = "H1";
        String header2 = "H2";

        String value1 = "v1";
        String value2 = "v2";
        String value3 = "v3,v33";
        String value4 = "v4";
        String value5 = "v5\tv55";
        String value6 = "v6";
        String value7 = "v7\nv77";
        String value8 = "v8";

        try (FileWriter fileWriter = new FileWriter(file);
             CSVPrinter printer = CSVFormat.DEFAULT
                     .withHeader(header1, header2)
                     .withSystemRecordSeparator()
                     .print(fileWriter)) {
            printer.printRecord(value1, value2);
            printer.printRecord(value3, value4);
            printer.printRecord(value5, value6);
            printer.printRecord(value7, value8);
        }

        try (Reader reader = new FileReader(file);
             CSVParser parser = CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withSystemRecordSeparator()
                     .parse(reader)) {

            List<String> headerNames = parser.getHeaderNames();
            assertThat(headerNames, contains(header1, header2));

            List<CSVRecord> records = parser.getRecords();
            assertThat(records, hasSize(4));

            CSVRecord record0 = records.get(0);
            assertThat(record0.get(header1), equalTo(value1));
            assertThat(record0.get(header2), equalTo(value2));

            CSVRecord record1 = records.get(1);
            assertThat(record1.get(header1), equalTo(value3));
            assertThat(record1.get(header2), equalTo(value4));

            CSVRecord record2 = records.get(2);
            assertThat(record2.get(header1), equalTo(value5));
            assertThat(record2.get(header2), equalTo(value6));

            CSVRecord record3 = records.get(3);
            assertThat(record3.get(header1), equalTo(value7));
            assertThat(record3.get(header2), equalTo(value8));
        }
    }

}