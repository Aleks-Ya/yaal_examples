package net.socket;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Catch {@link java.net.SocketTimeoutException} "Accept timed out" in server.
 */
@Disabled
class ClientSocketTimeoutTest {
    private static final String BODY = "abc";
    private static final int PORT = 2518;
    private static final int CLIENT_SOCKET_TIMEOUT = 3000;
    private boolean isServerReady = false;

    @Test
    void main() throws IOException, InterruptedException {
        runServer();
        runClient();
    }

    private void runServer() throws InterruptedException {
        new Thread(() -> {
            try (var serverSocket = new ServerSocket(PORT)) {
                System.out.println("Old socket operation timeout:" + serverSocket.getSoTimeout());
                serverSocket.setSoTimeout(CLIENT_SOCKET_TIMEOUT);
                System.out.println("New socket operation timeout:" + serverSocket.getSoTimeout());
                isServerReady = true;
                while (true) {
                    System.out.println("Server is waiting for client");
                    var clientSocket = serverSocket.accept();
                    System.out.println("Server accepted");
                    var pw = new PrintWriter(clientSocket.getOutputStream());
                    pw.write(BODY);
                    pw.flush();
                    clientSocket.close();
                    System.out.println("Server closed client socket");
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }).start();
        while (!isServerReady) {
            Thread.sleep(500);
        }
    }

    private void runClient() throws IOException, InterruptedException {
        var socket = new Socket("localhost", PORT);
        var is = new InputStreamReader((socket.getInputStream()));
        var firstByte = is.read();
        System.out.println("firstByte: " + firstByte);
        System.out.println("Client is stuck");
        Thread.sleep(CLIENT_SOCKET_TIMEOUT * 2);
    }
}
