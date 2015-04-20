package testclient;

import org.junit.Test;

public class VoltDbEmbeddedTest {

    @Test
    public void testServer() throws Exception {
        int port = TestServer.runServer();
        TestClient.runClient(port);
        TestServer.stopServer();
    }
}