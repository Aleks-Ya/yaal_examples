package io.jar;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.jar.JarFile;

import static org.assertj.core.api.Assertions.assertThat;

class ReadJarTest {

    @Test
    void read() throws IOException {
        var jarPath = Objects.requireNonNull(getClass().getResource("read_jar.jar")).getFile();
        Map<String, List<String>> fileNameToLinesMap = new HashMap<>();
        try (var jarFile = new JarFile(jarPath)) {
            var entryEnumeration = jarFile.entries();
            while (entryEnumeration.hasMoreElements()) {
                var entry = entryEnumeration.nextElement();
                var is = jarFile.getInputStream(entry);
                var dis = new BufferedReader(new InputStreamReader(is));
                List<String> lines = new ArrayList<>();
                String line;
                while ((line = dis.readLine()) != null) {
                    lines.add(line);
                }
                fileNameToLinesMap.put(entry.getName(), lines);
            }
        }
        assertThat(fileNameToLinesMap)
                .containsEntry("cities.txt", Arrays.asList("Moscow", "SPb"))
                .containsEntry("names.txt", Arrays.asList("John", "Mary"));
    }
}