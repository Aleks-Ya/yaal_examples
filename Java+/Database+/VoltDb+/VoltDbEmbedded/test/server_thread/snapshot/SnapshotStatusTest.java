package server_thread.snapshot;

import org.junit.Test;

/**
 * Вызов системной процедуры @SnapshotStatus.
 * JVM parameter: -Djava.library.path=libs
 */
public class SnapshotStatusTest {

    @Test
    public void testServer() throws Exception {
        int port = TestServer.runServer();
        SnapshotStatusClient.runClient(port);
        TestServer.stopServer();
    }
}