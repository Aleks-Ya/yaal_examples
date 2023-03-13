package sardine;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class EqualsTest {
    @Test
    void resourceEquals() throws IOException {
        var sardine = SFactory.initYandexDiskClient();
        var url = "https://webdav.yandex.ru";
        var resource1 = sardine.list(url, 0).get(0);
        var resource2 = sardine.list(url, 0).get(0);
        assertThat(resource1).isNotEqualTo(resource2);
        assertThat(resource1.getHref()).isEqualTo(resource2.getHref());
    }
}