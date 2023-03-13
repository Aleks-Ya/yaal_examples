package sardine;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BigFilesTest {
    private final Sardine sardine = SFactory.initYandexDiskClient();
    private final String rootUrl = "https://webdav.yandex.ru";
    private final Map<DavResource, Long> resourceSize = new HashMap<>();

    @Test
    void listBigFiles() throws IOException {
        var rootDir = "/docs/";
        var rootResource = sardine.list(rootUrl + rootDir, 0).get(0);
        handleResource(rootResource);
        resourceSize.keySet().stream()
                .sorted((resource1, resource2) -> resourceSize.get(resource2).compareTo(resourceSize.get(resource1)))
                .limit(10)
                .forEach(resource -> System.out.printf("%s\t%d\n", resource, resourceSize.get(resource)));
    }

    private void handleResource(DavResource res) {
        if (res.isDirectory()) {
            var nextUrl = rootUrl + res.getHref().toString();
            List<DavResource> resources;
            try {
                resources = sardine.list(nextUrl);
            } catch (Exception e) {
                throw new RuntimeException("Cannot get URL: " + nextUrl, e);
            }
            for (var r : resources) {
                if (r.getHref().equals(res.getHref())) {
                    continue;
                }
                handleResource(r);
            }
        } else {
            resourceSize.put(res, res.getContentLength());
        }
    }
}