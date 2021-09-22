package io.input_stream;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Covert InputStream to String.
 */
class InputStreamToString {
    private static final String CONTENT = "Hi!\nBye!";
    private final InputStream input = new ByteArrayInputStream(CONTENT.getBytes());

    @Test
    void byStreamJoining() throws IOException {
        String str;
        try (var buffer = new BufferedReader(new InputStreamReader(input))) {
            str = buffer.lines().collect(Collectors.joining("\n"));
        }
        assertThat(str, equalTo(CONTENT));
    }

    @Test
    void byInputStreamReader() throws IOException {
        var result = new StringBuilder();
        try (var isr = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            var c = new char[1];
            while (isr.read(c) != -1) {
                result.append(c);
            }
        }
        assertThat(result.toString(), equalTo(CONTENT));
    }

    @Test
    void byBufferedReader2() throws IOException {
        var result = new StringBuilder();
        try (var br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String s;
            while ((s = br.readLine()) != null) {
                result.append(s);
            }
        }
        var expected = CONTENT.replace("\n", "");
        assertThat(result.toString(), equalTo(expected));
    }
}
