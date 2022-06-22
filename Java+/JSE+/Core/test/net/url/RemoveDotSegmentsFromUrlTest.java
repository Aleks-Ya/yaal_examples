package net.url;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Remove dot segments (/./ and /../) from URL.
 */
class RemoveDotSegmentsFromUrlTest {
    @Test
    void removeDotSegments() {
        assertThat(RemoveDotSegmentsFromUrl.removeDotSegments("/a/b/../c")).isEqualTo("/a/c");
        assertThat(RemoveDotSegmentsFromUrl.removeDotSegments("../css/common.css")).isEqualTo("css/common.css");
    }

    /**
     * Examples from FRC 3986.
     */
    @Test
    void rfc1() {
        assertThat(RemoveDotSegmentsFromUrl.removeDotSegments("/a/b/c/./../../g")).isEqualTo("/a/g");
        assertThat(RemoveDotSegmentsFromUrl.removeDotSegments("mid/content=5/../6")).isEqualTo("mid/6");
    }
}