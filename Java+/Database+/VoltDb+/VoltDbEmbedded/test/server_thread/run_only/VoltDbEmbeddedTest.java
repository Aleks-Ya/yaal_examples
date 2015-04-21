package server_thread.run_only;

import org.junit.Test;

/**
 * JVM parameter: -Djava.library.path=libs
 */
public class VoltDbEmbeddedTest {

    @Test
    public void testServer() throws Exception {
        int port = TestServer.runServer();
        TestClient.runClient(port);
        TestServer.stopServer();
    }
}