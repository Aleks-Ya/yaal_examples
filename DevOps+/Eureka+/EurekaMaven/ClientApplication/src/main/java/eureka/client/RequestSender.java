package eureka.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.stream.Collectors;

class RequestSender {
    String readData(String hostname, int port) throws IOException {
        try (Socket socket = new Socket(hostname, port)) {
            BufferedReader bis = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            return bis.lines().collect(Collectors.joining("\n"));
        }
    }
}
