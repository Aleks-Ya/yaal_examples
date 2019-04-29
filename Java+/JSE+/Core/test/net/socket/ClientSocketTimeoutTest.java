package net.socket;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Catch {@link java.net.SocketTimeoutException} "Accept timed out" in server.
 */
public class ClientSocketTimeoutTest {
    private static final String BODY = "abc";
    private static final int PORT = 2512;
    private static final int CLIENT_SOCKET_TIMEOUT = 3000;
    private boolean isServerReady = false;

    @Test
    public void main() throws IOException, InterruptedException {
        runServer();
        runClient();
    }

    private void runServer() throws InterruptedException {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Old socket operation timeout:" + serverSocket.getSoTimeout());
                serverSocket.setSoTimeout(CLIENT_SOCKET_TIMEOUT);
                System.out.println("New socket operation timeout:" + serverSocket.getSoTimeout());
                isServerReady = true;
                while (true) {
                    System.out.println("Server is waiting for client");
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Server accepted");
                    PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
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
        Socket socket = new Socket("localhost", PORT);
        InputStreamReader is = new InputStreamReader((socket.getInputStream()));
        int firstByte = is.read();
        System.out.println("firstByte: " + firstByte);
        System.out.println("Client is stuck");
        Thread.sleep(CLIENT_SOCKET_TIMEOUT * 2);
    }
}
