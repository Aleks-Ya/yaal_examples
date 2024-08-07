package net.socket;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 *
 */
class AcknowledgementCharacterTest {

    @Test
    @Disabled("Not finished")
    public void shutdown() throws IOException, InterruptedException {
        var serverSocket = new ServerSocket(23512);
        System.out.println("Server created");
        var t = new Thread(() -> {
            try {
                System.out.println("Wait socket");
                serverSocket.accept();
            } catch (SocketException e) {
                System.out.println("ServerSocket closed");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        Thread.sleep(300);
        System.out.println("Stop server");
        serverSocket.close();
    }
}
