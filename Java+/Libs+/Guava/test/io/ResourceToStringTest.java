package io;

import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

class ResourceToStringTest {

    @Test
    void readResourceToString() throws IOException {
        var url = Resources.getResource("io/ResourceToStringTest.txt");
        var content = Resources.toString(url, UTF_8);
        assertThat(content).isEqualTo("abc");
    }

    @Test
    void readResourceToStringWithContext() throws IOException {
        var url = Resources.getResource(ResourceToStringTest.class, "ResourceToStringTest.txt");
        var content = Resources.toString(url, UTF_8);
        assertThat(content).isEqualTo("abc");
    }
}