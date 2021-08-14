package disk;

import com.yandex.disk.rest.ResourcesArgs;
import com.yandex.disk.rest.RestClient;
import com.yandex.disk.rest.exceptions.ServerIOException;
import com.yandex.disk.rest.json.Resource;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class BiggestVideos {

    @Test
    void findBiggestVideos() throws IOException, ServerIOException {
        var client = ClientFactory.getInstance();
        var rootFolder = "/";
        var videoNumber = 50;
        var biggestVideos = findVideos(client, rootFolder, videoNumber);
        printVideos(biggestVideos);
    }

    private List<Resource> findVideos(RestClient client, String rootFolder, int videoNumber) throws IOException, ServerIOException {
        System.out.println("Root folder: " + rootFolder);
        var biggestList = new BiggestResourceList(videoNumber);
        var offset = 0;
        var limit = 1000;
        List<Resource> items;
        do {
            var resourceArgs = new ResourcesArgs.Builder()
                    .setLimit(limit)
                    .setOffset(offset)
                    .setFields("items.path,items.size")
                    .setMediaType("video")
                    .build();
            var list = client.getFlatResourceList(resourceArgs);
            items = list.getItems();
            for (var newRes : items) {
                if (newRes.getPath().getPath().startsWith(rootFolder)) {
                    biggestList.addResource(newRes);
                }
            }
            offset += items.size();
            System.out.println("Offset: " + offset);
        } while (!items.isEmpty());
        return biggestList.getBiggestResources();
    }

    private void printVideos(List<Resource> biggestResources) {
        var totalSizeMb = 0L;
        var locale = Locale.forLanguageTag("RU");
        for (var i = 0; i < biggestResources.size(); i++) {
            var res = biggestResources.get(i);
            var sizeMb = res.getSize() / 1024 / 1024;
            System.out.printf(locale, "%2d) %s (%,d Mb)\n", i + 1, res.getPath().getPath(), sizeMb);
            totalSizeMb += sizeMb;
        }
        System.out.printf(locale, "Total size: %,d Mb\n", totalSizeMb);
    }

    private static class BiggestResourceList {
        private final List<Resource> resources;
        private final int size;

        BiggestResourceList(int size) {
            this.size = size;
            resources = new ArrayList<>(size);
        }

        void addResource(Resource newRes) {
            if (resources.isEmpty()) {
                resources.add(newRes);
            } else {
                for (var i = 0; i < resources.size(); i++) {
                    var oldRes = resources.get(i);
                    if (oldRes == null || oldRes.getSize() < newRes.getSize()) {
                        resources.add(i, newRes);
                        while (resources.size() > size) {
                            resources.remove(resources.size() - 1);
                        }
                        break;
                    }
                }
            }
        }

        List<Resource> getBiggestResources() {
            return resources;
        }
    }
}
