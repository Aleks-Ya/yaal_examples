package io.print_writer;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class PrintWriterTest {

    @Test
    void printWriterToString() {
        var out = new StringWriter();
        var pw = new PrintWriter(out);
        var text = "abc";
        pw.print(text);
        assertThat(out.toString(), equalTo(text));
    }

}