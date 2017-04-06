package runner;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.util.ServerRunner;

public class ExecuteInstanceMain {

    public static void main(String[] args) {
        NanoHTTPD server = new HelloServer();
        ServerRunner.executeInstance(server);
    }
}
