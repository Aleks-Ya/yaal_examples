package disk;

import com.yandex.disk.rest.exceptions.ServerIOException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class DiskInfoTest {
    @Test
    void getDiskInfo() throws IOException, ServerIOException {
        var client = ClientFactory.getInstance();
        var diskInfo = client.getDiskInfo();
        System.out.println(diskInfo);
    }
}
