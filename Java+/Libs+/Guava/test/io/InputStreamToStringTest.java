package io;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

class InputStreamToStringTest {

    @Test
    void byCharStreams() throws IOException {
        var exp = "abc";
        Readable r = new StringReader(exp);
        var act = CharStreams.toString(r);
        assertThat(act).isEqualTo(exp);
    }

    @Test
    void byByteStreams() throws IOException {
        var exp = "abc".getBytes();
        InputStream is = new ByteArrayInputStream(exp);
        var act = ByteStreams.toByteArray(is);
        assertThat(act).isEqualTo(exp);
    }
}