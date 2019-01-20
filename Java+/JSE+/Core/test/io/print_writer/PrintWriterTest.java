package io.print_writer;

import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class PrintWriterTest {

    @Test
    public void printWriterToString() {
        StringWriter out = new StringWriter();
        PrintWriter pw = new PrintWriter(out);
        String text = "abc";
        pw.print(text);
        assertThat(out.toString(), equalTo(text));
    }

}