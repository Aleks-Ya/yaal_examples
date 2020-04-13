package io.jar;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;

public class ReadJarTest {

    @Test
    public void read() throws IOException {
        String jarPath = getClass().getResource("read_jar.jar").getFile();
        Map<String, List<String>> fileNameToLinesMap = new HashMap<>();
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entryEnumeration = jarFile.entries();
            while (entryEnumeration.hasMoreElements()) {
                JarEntry entry = entryEnumeration.nextElement();
                InputStream is = jarFile.getInputStream(entry);
                BufferedReader dis = new BufferedReader(new InputStreamReader(is));
                List<String> lines = new ArrayList<>();
                String line;
                while ((line = dis.readLine()) != null) {
                    lines.add(line);
                }
                fileNameToLinesMap.put(entry.getName(), lines);
            }
        }
        assertThat(fileNameToLinesMap, hasEntry("cities.txt", Arrays.asList("Moscow", "SPb")));
        assertThat(fileNameToLinesMap, hasEntry("names.txt", Arrays.asList("John", "Mary")));

    }
}