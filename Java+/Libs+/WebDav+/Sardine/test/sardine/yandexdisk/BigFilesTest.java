package sardine.yandexdisk;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

class BigFilesTest {
    private final Sardine sardine = SFactory.initYandexDiskClient();
    private final String rootUrl = "https://webdav.yandex.ru";
    private final Map<String, Long> resourceSize = new HashMap<>();
    private final File cache = new File(System.getProperty("user.home"), "BigFilesTest.properties");
    private final Properties propertyCache = new Properties();
    private long requestCounter = 0;
    private long readFromCacheCounter = 0;

    @Test
    void listBigFiles() throws IOException {
        if (cache.exists()) {
            propertyCache.load(new FileReader(cache));
            propertyCache.forEach((key, value) -> resourceSize.put(key.toString(), Long.valueOf(value.toString())));
        }
        var rootDir = "/docs/";
        var rootResource = request(rootUrl + rootDir, 0).get(0);
        var size = handleResource(rootResource);
        System.out.println("Total size: " + size);
        resourceSize.keySet().stream()
                .filter(resource -> !resource.endsWith("/"))
                .sorted((resource1, resource2) -> resourceSize.get(resource2).compareTo(resourceSize.get(resource1)))
                .limit(10)
                .forEach(resource -> System.out.printf("%s\t%d\n", resource, resourceSize.get(resource)));
        storeCache();
        printCacheInfo();
    }

    private long handleResource(DavResource res) throws IOException {
        var key = res.toString();
        if (propertyCache.containsKey(key)) {
            var size = Long.parseLong(propertyCache.getProperty(key));
            resourceSize.put(key, size);
            readFromCacheCounter++;
            return size;
        }
        if (res.isDirectory()) {
            var nextUrl = rootUrl + res.getHref().toString();
            var resources = request(nextUrl, 1);
            var totalSize = 0L;
            for (var r : resources) {
                if (r.getHref().equals(res.getHref())) {
                    System.out.println("Current folder: " + key);
                    storeCache();//Store once per folder
                    printCacheInfo();
                    continue;
                }
                totalSize += handleResource(r);
            }
            propertyCache.put(key, String.valueOf(totalSize));
            return totalSize;
        } else {
            var size = res.getContentLength();
            propertyCache.put(key, String.valueOf(size));
            resourceSize.put(key, size);
            return size;
        }
    }

    private void storeCache() throws IOException {
        propertyCache.store(new FileOutputStream(cache), null);
    }

    private List<DavResource> request(String nextUrl, int depth) {
        List<DavResource> resources;
        try {
            resources = sardine.list(nextUrl, depth);
            requestCounter++;
        } catch (Exception e) {
            throw new RuntimeException("Cannot get URL: " + nextUrl, e);
        }
        return resources;
    }

    private void printCacheInfo() {
        System.out.printf("Cache size: %d, requestCounter: %d, readFromCacheCounter: %d\n",
                propertyCache.size(), requestCounter, readFromCacheCounter);
    }
}