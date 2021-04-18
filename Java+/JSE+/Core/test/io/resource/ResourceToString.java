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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceToString {
    private static final String EXP_CONTENT = "abc\ndef";
    private static final String RESOURCE_NAME = "ResourceToString.txt";

    @Test
    public void readAllLines() throws IOException {
        var resourceUrl = ResourceToString.class.getResource(RESOURCE_NAME);
        var path = new File(resourceUrl.getFile()).toPath();
        var lines = Files.readAllLines(path);
        var actContent = lines.stream().collect(Collectors.joining("\n"));
        assertEquals(EXP_CONTENT, actContent);
    }

    @Test
    public void readAllLines2() throws IOException, URISyntaxException {
        var resourceUrl = ResourceToString.class.getResource(RESOURCE_NAME);
        var path = Paths.get(resourceUrl.toURI());
        var lines = Files.readAllLines(path);
        var actContent = lines.stream().collect(Collectors.joining("\n"));
        assertEquals(EXP_CONTENT, actContent);
    }

    @Test
    public void readAllBytes() throws IOException {
        var resourceUrl = ResourceToString.class.getResource(RESOURCE_NAME);
        var path = new File(resourceUrl.getFile()).toPath();
        var actContent = new String(Files.readAllBytes(path));
        assertEquals(EXP_CONTENT, actContent);
    }

    @Test
    public void readAllBytes2() throws IOException, URISyntaxException {
        var resourceUrl = ResourceToString.class.getResource(RESOURCE_NAME);
        var path = Paths.get(resourceUrl.toURI());
        var actContent = new String(Files.readAllBytes(path));
        assertEquals(EXP_CONTENT, actContent);
    }

    @Test
    public void bufferedReader() {
        var is = ResourceToString.class.getResourceAsStream(RESOURCE_NAME);
        var reader = new BufferedReader(new InputStreamReader(is));
        var actContent = reader.lines().collect(Collectors.joining("\n"));
        assertEquals(EXP_CONTENT, actContent);
    }
}
