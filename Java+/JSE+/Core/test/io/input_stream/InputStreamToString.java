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
public class InputStreamToString {
    private static final String CONTENT = "Hi!\nBye!";
    private final InputStream input = new ByteArrayInputStream(CONTENT.getBytes());

    @Test
    public void byStreamJoining() throws IOException {
        String str;
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            str = buffer.lines().collect(Collectors.joining("\n"));
        }
        assertThat(str, equalTo(CONTENT));
    }

    @Test
    public void byInputStreamReader() throws IOException {
        StringBuilder result = new StringBuilder();
        try (InputStreamReader isr = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            char[] c = new char[1];
            while (isr.read(c) != -1) {
                result.append(c);
            }
        }
        assertThat(result.toString(), equalTo(CONTENT));
    }

    @Test
    public void byBufferedReader2() throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String s;
            while ((s = br.readLine()) != null) {
                result.append(s);
            }
        }
        String expected = CONTENT.replace("\n", "");
        assertThat(result.toString(), equalTo(expected));
    }
}
