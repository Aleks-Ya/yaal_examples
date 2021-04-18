package runner;

import fi.iki.elonen.NanoHTTPD;

import java.util.List;
import java.util.Map;

public class HelloServer extends NanoHTTPD {
    HelloServer() {
        super(8080);
    }

    @Override
    public Response serve(IHTTPSession session) {
        Method method = session.getMethod();
        String uri = session.getUri();
        System.out.println(method + " '" + uri + "' ");

        String msg = "<html><body><h1>Hello server</h1>\n";
        Map<String, List<String>> params = session.getParameters();
        if (params.get("username") == null) {
            msg += "<form action='?' method='get'>\n" + "  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
        } else {
            msg += "<p>Hello, " + params.get("username") + "!</p>";
        }

        msg += "</body></html>\n";
        return newFixedLengthResponse(msg);
    }
}
