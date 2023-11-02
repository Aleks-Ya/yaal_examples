package csv.print;

import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static org.assertj.core.api.Assertions.assertThat;

class PrintTest {

    @Test
    void simpleValue() throws IOException {
        var baos = new ByteArrayOutputStream();
        var out = new OutputStreamWriter(baos);
        var format = CSVFormat.DEFAULT.builder()
                .setHeader("H1", "H2")
                .setRecordSeparator(System.lineSeparator())
                .build();
        try (var printer = format.print(out)) {
            printer.printRecord("v1", "v2");
            printer.printRecord("v3", "v4");
        }
        var act = baos.toString();
        var exp = "H1,H2\nv1,v2\nv3,v4\n";
        assertThat(act).isEqualTo(exp);
    }

    @Test
    void valueContainsDelimiter() throws IOException {
        var baos = new ByteArrayOutputStream();
        var out = new OutputStreamWriter(baos);
        var format = CSVFormat.DEFAULT.builder()
                .setHeader("H1", "H2")
                .setRecordSeparator(System.lineSeparator())
                .build();
        try (var printer = format.print(out)) {
            printer.printRecord("v1,v2", "v3");
            printer.printRecord("v4", "v5,v6");
        }
        var act = baos.toString();
        var exp = "H1,H2\n\"v1,v2\",v3\nv4,\"v5,v6\"\n";
        assertThat(act).isEqualTo(exp);
    }

    @Test
    void valueContainsTab() throws IOException {
        var baos = new ByteArrayOutputStream();
        var out = new OutputStreamWriter(baos);
        var format = CSVFormat.DEFAULT.builder()
                .setHeader("H1", "H2")
                .setRecordSeparator(System.lineSeparator())
                .build();
        try (var printer = format.print(out)) {
            printer.printRecord("v1\tv2", "v3");
            printer.printRecord("v4", "v5\tv6");
        }
        var act = baos.toString();
        var exp = "H1,H2\nv1\tv2,v3\nv4,v5\tv6\n";
        assertThat(act).isEqualTo(exp);
    }

    @Test
    void valueContainsLineTermination() throws IOException {
        var baos = new ByteArrayOutputStream();
        var out = new OutputStreamWriter(baos);
        var format = CSVFormat.DEFAULT.builder()
                .setHeader("H1", "H2")
                .setRecordSeparator(System.lineSeparator())
                .build();
        try (var printer = format.print(out)) {
            printer.printRecord("v1\nv2", "v3");
            printer.printRecord("v4", "v5\nv6");
        }
        var act = baos.toString();
        var exp = "H1,H2\n\"v1\nv2\",v3\nv4,\"v5\nv6\"\n";
        assertThat(act).isEqualTo(exp);
    }

    @Test
    void tabDelimiter() throws IOException {
        var baos = new ByteArrayOutputStream();
        var out = new OutputStreamWriter(baos);
        var delimiter = '\t';
        var format = CSVFormat.DEFAULT.builder()
                .setHeader("H1", "H2")
                .setDelimiter(delimiter)
                .setRecordSeparator(System.lineSeparator())
                .build();
        try (var printer = format.print(out)) {
            printer.printRecord("v1\tv2", "v3");
            printer.printRecord("v4", "v5\tv6");
        }
        var act = baos.toString();
        var exp = "H1\tH2\n\"v1\tv2\"\tv3\nv4\t\"v5\tv6\"\n";
        assertThat(act).isEqualTo(exp);
    }

}