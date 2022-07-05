package server_thread.run_only;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcCallException;
import server_thread.ServerThreadHelper;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * JVM parameter: -Djava.library.path=libs
 */
class InsertTest {
    private static Client client;

    @BeforeAll
    public static void beforeClass() throws Exception {
        var port = ServerThreadHelper.runServer(MyTableInsertProcedure.class);
        client = ClientFactory.createClient();
        client.createConnection("localhost", port);
    }

    @AfterAll
    public static void tearDown() {
        ServerThreadHelper.stopServer();
    }

    /**
     * С помощью системной хранимой процедуры @AdHoc.
     */
    @Test
    void adHoc() throws IOException, ProcCallException {
        var response = client.callProcedure("@AdHoc", "insert into my_table (id, number, text) values (1, 33, 'message');");
        assertThat(response.getStatus()).isEqualTo(ClientResponse.SUCCESS);
    }

    /**
     * С помощью автоматически генерируемой хранимой процедуры для таблицы (default procedure).
     */
    @Test
    void defaultProcedure() throws IOException, ProcCallException {
        var response = client.callProcedure("MY_TABLE.insert", 2, 44, "insert message");
        assertThat(response.getStatus()).isEqualTo(ClientResponse.SUCCESS);
    }

    /**
     * С помощью самописной хранимой процедуры.
     */
    @Test
    void customProcedure() throws IOException, ProcCallException {
        var response = client.callProcedure("MyTableInsertProcedure", 3, 55, "insert message");
        assertThat(response.getStatus()).isEqualTo(ClientResponse.SUCCESS);
    }
}
