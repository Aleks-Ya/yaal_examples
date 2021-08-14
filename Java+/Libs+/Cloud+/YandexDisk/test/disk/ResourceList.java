package disk;

import com.yandex.disk.rest.ResourcesArgs;
import com.yandex.disk.rest.exceptions.ServerIOException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ResourceList {

    @Test
    void getVideoFiles() throws IOException, ServerIOException {
        var client = ClientFactory.getInstance();
        var resourceArgs = new ResourcesArgs.Builder()
                .setLimit(5)
                .setFields("items.path,items.name,items.size,items.type")
                .setMediaType("video")
                .build();
        var list = client.getFlatResourceList(resourceArgs);
        var items = list.getItems();
        System.out.println(items);
    }
}
