package server_thread.run_only;

import org.junit.jupiter.api.Test;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import server_thread.ServerThreadHelper;

/**
 * JVM parameter: -Djava.library.path=libs
 */
class CallProcedureTest {

    @Test
    void testServer() throws Exception {
        var port = ServerThreadHelper.runServer();

        Client client = null;
        try {
            client = ClientFactory.createClient();
            client.createConnection("localhost", port);

            // Perform the client actions.
            client.callProcedure("TestProcedure", 1, 111, "some text");
            // More actions go here.
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ServerThreadHelper.stopServer();
    }
}