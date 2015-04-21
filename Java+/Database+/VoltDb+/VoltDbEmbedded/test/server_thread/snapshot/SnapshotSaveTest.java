package server_thread.snapshot;

import org.junit.Test;

/**
 * Сохранение снимка БД с помощью системной процедуры @SnapshotSave.
 * JVM parameter: -Djava.library.path=libs
 */
public class SnapshotSaveTest {

    @Test
    public void testServer() throws Exception {
        int port = TestServer.runServer();
        SnapshotSaveClient.runClient(port);
        TestServer.stopServer();
    }
}