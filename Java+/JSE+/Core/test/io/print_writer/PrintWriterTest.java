package io.print_writer;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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