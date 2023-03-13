package sardine;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class SardineTest {
    private final Sardine sardine = SFactory.initYandexDiskClient();

    @Test
    void listRootFiles() throws IOException {
        var resources = sardine.list("https://webdav.yandex.ru");
        assertThat(resources).hasSizeGreaterThan(1);
        assertThat(resources).first().extracting(DavResource::getPath).isEqualTo("/");
        for (var res : resources) {
            System.out.println(res);
        }
    }

    @Test
    void listSingleRootResource() throws IOException {
        var resources = sardine.list("https://webdav.yandex.ru", 0);
        assertThat(resources).singleElement().extracting(DavResource::getPath).isEqualTo("/");
        for (var res : resources) {
            System.out.println(res);
        }
    }

    @Test
    void listNotRootResource() throws IOException {
        var resources = sardine.list("https://webdav.yandex.ru/docs/", 0);
        assertThat(resources).singleElement().extracting(DavResource::getPath).isEqualTo("/docs/");
        for (var res : resources) {
            System.out.println(res);
        }
    }
}