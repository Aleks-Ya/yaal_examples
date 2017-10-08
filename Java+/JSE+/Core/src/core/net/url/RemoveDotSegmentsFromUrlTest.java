package core.net.url;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Remove dot segments (/./ and /../) from URL.
 */
public class RemoveDotSegmentsFromUrlTest {
    @Test
    public void removeDotSegments() {
        Assert.assertThat(RemoveDotSegmentsFromUrl.removeDotSegments("/a/b/../c"), Matchers.equalTo("/a/c"));
        Assert.assertThat(RemoveDotSegmentsFromUrl.removeDotSegments("../css/common.css"), Matchers.equalTo("css/common.css"));
    }

    /**
     * Examples from FRC 3986.
     */
    @Test
    public void rfc1() {
        Assert.assertThat(RemoveDotSegmentsFromUrl.removeDotSegments("/a/b/c/./../../g"), Matchers.equalTo("/a/g"));
        Assert.assertThat(RemoveDotSegmentsFromUrl.removeDotSegments("mid/content=5/../6"), Matchers.equalTo("mid/6"));
    }
}