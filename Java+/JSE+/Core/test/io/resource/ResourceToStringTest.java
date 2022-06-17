package io.resource;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceToStringTest {
    private static final String EXP_CONTENT = "abc\ndef";
    private static final String RESOURCE_NAME = "ResourceToString.txt";

    @Test
    void readAllLines() throws IOException {
        var resourceUrl = ResourceToStringTest.class.getResource(RESOURCE_NAME);
        var path = new File(resourceUrl.getFile()).toPath();
        var lines = Files.readAllLines(path);
        var actContent = lines.stream().collect(Collectors.joining("\n"));
        assertThat(EXP_CONTENT).isEqualTo(actContent);
    }

    @Test
    void readAllLines2() throws IOException, URISyntaxException {
        var resourceUrl = ResourceToStringTest.class.getResource(RESOURCE_NAME);
        var path = Paths.get(resourceUrl.toURI());
        var lines = Files.readAllLines(path);
        var actContent = lines.stream().collect(Collectors.joining("\n"));
        assertThat(EXP_CONTENT).isEqualTo(actContent);
    }

    @Test
    void readAllBytes() throws IOException {
        var resourceUrl = ResourceToStringTest.class.getResource(RESOURCE_NAME);
        var path = new File(resourceUrl.getFile()).toPath();
        var actContent = new String(Files.readAllBytes(path));
        assertThat(EXP_CONTENT).isEqualTo(actContent);
    }

    @Test
    void readAllBytes2() throws IOException, URISyntaxException {
        var resourceUrl = ResourceToStringTest.class.getResource(RESOURCE_NAME);
        var path = Paths.get(resourceUrl.toURI());
        var actContent = new String(Files.readAllBytes(path));
        assertThat(EXP_CONTENT).isEqualTo(actContent);
    }

    @Test
    void bufferedReader() {
        var is = ResourceToStringTest.class.getResourceAsStream(RESOURCE_NAME);
        var reader = new BufferedReader(new InputStreamReader(is));
        var actContent = reader.lines().collect(Collectors.joining("\n"));
        assertThat(EXP_CONTENT).isEqualTo(actContent);
    }
}
