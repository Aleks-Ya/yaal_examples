package io.print_writer;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

class PrintWriterTest {

    @Test
    void printWriterToString() {
        var out = new StringWriter();
        var pw = new PrintWriter(out);
        var text = "abc";
        pw.print(text);
        assertThat(out).hasToString(text);
    }

}