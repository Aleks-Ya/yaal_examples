package sardine;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class EqualsTest extends BaseWebDavTest {
    @Test
    void resourceEquals() throws IOException {
        var sardine = newSardineClient();
        var url = getBaseUri().resolve("/data.txt").toString();
        var data = "abc";
        sardine.put(url, data.getBytes(StandardCharsets.UTF_8));

        var resource1 = sardine.list(url, 0).get(0);
        var resource2 = sardine.list(url, 0).get(0);
        assertThat(resource1).isNotEqualTo(resource2);
        assertThat(resource1.getHref()).isEqualTo(resource2.getHref());
    }
}