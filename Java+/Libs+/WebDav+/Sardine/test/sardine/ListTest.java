package sardine;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ListTest extends BaseWebDavTest {
    private final Sardine sardine = newSardineClient();

    @BeforeEach
    void beforeEach() {
        uploadResource("/", "f1.txt", "f1");
        uploadResource("/d1", "f2.txt", "f2");
    }

    @Test
    void listRootFiles() throws IOException {
        var url = getBaseUri().toString();
        var resources = sardine.list(url);
        assertThat(resources).map(DavResource::getPath).containsExactlyInAnyOrder("/", "/d1/", "/f1.txt");
    }

    @Test
    void listSingleRootResource() throws IOException {
        var url = getBaseUri().toString();
        var resources = sardine.list(url, 0);
        assertThat(resources).map(DavResource::getPath).containsExactlyInAnyOrder("/");
    }

    @Test
    void listNotRootResource() throws IOException {
        var url = getBaseUri().resolve("/d1").toString();
        var resources = sardine.list(url, 0);
        assertThat(resources).map(DavResource::getPath).containsExactlyInAnyOrder("/d1/");
    }
}