package io.resource;

import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class ResourceToString {
    private static final String EXP_CONTENT = "abc\ndef";
    private static final String RESOURCE_NAME = "ResourceToString.txt";

    @Test
    public void readAllLines() throws IOException {
        URL resourceUrl = ResourceToString.class.getResource(RESOURCE_NAME);
        Path path = new File(resourceUrl.getFile()).toPath();
        List<String> lines = Files.readAllLines(path);
        String actContent = lines.stream().collect(Collectors.joining("\n"));
        assertEquals(EXP_CONTENT, actContent);
    }

    @Test
    public void readAllLines2() throws IOException, URISyntaxException {
        URL resourceUrl = ResourceToString.class.getResource(RESOURCE_NAME);
        Path path = Paths.get(resourceUrl.toURI());
        List<String> lines = Files.readAllLines(path);
        String actContent = lines.stream().collect(Collectors.joining("\n"));
        assertEquals(EXP_CONTENT, actContent);
    }

    @Test
    public void readAllBytes() throws IOException {
        URL resourceUrl = ResourceToString.class.getResource(RESOURCE_NAME);
        Path path = new File(resourceUrl.getFile()).toPath();
        String actContent = new String(Files.readAllBytes(path));
        assertEquals(EXP_CONTENT, actContent);
    }

    @Test
    public void readAllBytes2() throws IOException, URISyntaxException {
        URL resourceUrl = ResourceToString.class.getResource(RESOURCE_NAME);
        Path path = Paths.get(resourceUrl.toURI());
        String actContent = new String(Files.readAllBytes(path));
        assertEquals(EXP_CONTENT, actContent);
    }

    @Test
    public void bufferedReader() {
        InputStream is = ResourceToString.class.getResourceAsStream(RESOURCE_NAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String actContent = reader.lines().collect(Collectors.joining("\n"));
        assertEquals(EXP_CONTENT, actContent);
    }
}
