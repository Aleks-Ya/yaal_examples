package io;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class InputStreamToStringTest {

    @Test
    public void byCharStreams() throws IOException {
        String exp = "abc";
        Readable r = new StringReader(exp);
        String act = CharStreams.toString(r);
        assertThat(act, equalTo(exp));
    }

    @Test
    public void byByteStreams() throws IOException {
        byte[] exp = "abc".getBytes();
        InputStream is = new ByteArrayInputStream(exp);
        byte[] act = ByteStreams.toByteArray(is);
        assertThat(act, equalTo(exp));
    }
}