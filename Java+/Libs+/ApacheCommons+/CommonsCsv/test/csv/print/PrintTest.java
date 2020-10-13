package csv.print;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PrintTest {

    @Test
    public void simpleValue() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Appendable out = new OutputStreamWriter(baos);
        try (CSVPrinter printer = CSVFormat.DEFAULT
                .withHeader("H1", "H2")
                .withSystemRecordSeparator()
                .print(out)) {
            printer.printRecord("v1", "v2");
            printer.printRecord("v3", "v4");
        }
        String act = new String(baos.toByteArray());
        String exp = "H1,H2\nv1,v2\nv3,v4\n";
        assertThat(act, equalTo(exp));
    }

    @Test
    public void valueContainsDelimiter() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Appendable out = new OutputStreamWriter(baos);
        try (CSVPrinter printer = CSVFormat.DEFAULT
                .withHeader("H1", "H2")
                .withSystemRecordSeparator()
                .print(out)) {
            printer.printRecord("v1,v2", "v3");
            printer.printRecord("v4", "v5,v6");
        }
        String act = new String(baos.toByteArray());
        String exp = "H1,H2\n\"v1,v2\",v3\nv4,\"v5,v6\"\n";
        assertThat(act, equalTo(exp));
    }

    @Test
    public void valueContainsTab() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Appendable out = new OutputStreamWriter(baos);
        try (CSVPrinter printer = CSVFormat.DEFAULT
                .withHeader("H1", "H2")
                .withSystemRecordSeparator()
                .print(out)) {
            printer.printRecord("v1\tv2", "v3");
            printer.printRecord("v4", "v5\tv6");
        }
        String act = new String(baos.toByteArray());
        String exp = "H1,H2\nv1\tv2,v3\nv4,v5\tv6\n";
        assertThat(act, equalTo(exp));
    }

    @Test
    public void valueContainsLineTermination() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Appendable out = new OutputStreamWriter(baos);
        try (CSVPrinter printer = CSVFormat.DEFAULT
                .withHeader("H1", "H2")
                .withSystemRecordSeparator()
                .print(out)) {
            printer.printRecord("v1\nv2", "v3");
            printer.printRecord("v4", "v5\nv6");
        }
        String act = new String(baos.toByteArray());
        String exp = "H1,H2\n\"v1\nv2\",v3\nv4,\"v5\nv6\"\n";
        assertThat(act, equalTo(exp));
    }

}