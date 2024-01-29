package io.resource;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GetResourcesDirTest {

    @Test
    void classResourceEmpty() {
        var url = GetResourcesDirTest.class.getResource("");
        assertThat(url).asString().endsWith("Java+/JSE+/Core/build/classes/java/test/io/resource/");
    }

    @Test
    void classResourceDot() {
        var url = GetResourcesDirTest.class.getResource(".");
        assertThat(url).asString().endsWith("Java+/JSE+/Core/build/classes/java/test/io/resource/");
    }

    @Test
    void classResourceSlash() {
        var url = GetResourcesDirTest.class.getResource("/");
        assertThat(url).asString().endsWith("Java+/JSE+/Core/build/classes/java/test/");
    }

    @Test
    void classResourceSlashDir() {
        var url = GetResourcesDirTest.class.getResource("/io");
        assertThat(url).asString().endsWith("Java+/JSE+/Core/build/classes/java/test/io");
    }

    @Test
    void classLoaderResource() {
        var resource = ClassLoader.getSystemClassLoader().getResource(".");
        assertThat(resource).asString().endsWith("Java+/JSE+/Core/build/classes/java/test/");
    }

    @Test
    void nullPath() {
        //noinspection DataFlowIssue
        assertThatThrownBy(() -> GetResourcesDirTest.class.getResource(null))
                .isInstanceOf(NullPointerException.class);
    }
}
