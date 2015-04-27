package server_thread.run_only;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import server_thread.ServerThreadHelper;

/**
 * @author yablokov a.
 */
public class Insert {
    private static Client client;

    @BeforeClass
    public  static void beforeClass() throws Exception {
        int port = ServerThreadHelper.runServer();
        client = ClientFactory.createClient();
        client.createConnection("localhost", port);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        ServerThreadHelper.stopServer();
    }

    @Test
    public void insert() throws Exception {
        client.callProcedure("@AdHoc", "insert into t1 (id, number, text) values (1, 33, 'message');");
    }

}
