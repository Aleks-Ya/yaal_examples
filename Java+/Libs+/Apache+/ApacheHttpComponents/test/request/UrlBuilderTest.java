package request;

import org.apache.hc.core5.net.URIBuilder;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class UrlBuilderTest {
    @Test
    void build() throws URISyntaxException {
        var uri = new URIBuilder("https://example.com")
                .setPathSegments("resources", "file name with spaces", "subfolder")
                .setParameter("param1", "value1")
                .setParameter("param2", "value with spaces")
                .build();
        var str = uri.toString();
        assertThat(str).isEqualTo(
                "https://example.com/resources/file%20name%20with%20spaces/subfolder?param1=value1&param2=value+with+spaces");
    }
}