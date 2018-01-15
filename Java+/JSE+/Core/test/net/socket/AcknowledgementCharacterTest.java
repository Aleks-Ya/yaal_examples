package net.socket;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 *
 */
public class AcknowledgementCharacterTest {

    @Test
    @Ignore("Not finished")
    public void shutdown() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(23512);
        System.out.println("Server created");
        Thread t = new Thread(() -> {
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
