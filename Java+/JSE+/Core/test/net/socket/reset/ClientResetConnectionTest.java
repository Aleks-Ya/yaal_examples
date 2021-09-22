package net.socket.reset;

import org.junit.jupiter.api.Test;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Client resets the connection (sends RST flag to Server).
 */
class ClientResetConnectionTest {
    private static final int PORT = 2516;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Test
    void clientGotDown() throws InterruptedException, ExecutionException {
        var executor = Executors.newFixedThreadPool(2);
        var serverFuture = executor.submit(serverCallable());
        var clientFuture = executor.submit(clientCallable());
        try {
            serverFuture.get();
        } catch (ExecutionException e) {
            System.out.println("Server got SocketException");
            var cause = (SocketException) e.getCause();
            assertThat(cause.getMessage()).containsSubsequence("Connection reset by peer");
            clientFuture.get();
            executor.shutdown();
            return;
        }
        throw new AssertionError("Expected ExecutionException");
    }

    private Callable<Void> serverCallable() {
        return () -> {
            try (var serverSocket = new ServerSocket(PORT)) {
                latch.countDown();
                System.out.println("Server is waiting for client");
                var clientSocket = serverSocket.accept();
                System.out.println("Server accepted");
                var pw = clientSocket.getOutputStream();
                //noinspection InfiniteLoopStatement
                while (true) {
                    pw.write("abc".getBytes());
                }
            }
        };
    }

    private Callable<Void> clientCallable() {
        return () -> {
            System.out.println("Client is waiting Server");
            latch.await();
            System.out.println("Client is reading data from Server");
            var sb = new StringBuilder();
            var socket = new Socket("localhost", PORT);
            var is = socket.getInputStream();
            int i;
            while ((i = is.read()) != -1) {
                sb.append((char) i);
                System.out.println("sb size " + sb.length());
                if (sb.length() > 10) {
                    socket.setSoLinger(true, 0);
                    socket.close();
                    System.out.println("Client has reset connection");
                    return null;
                }
            }
            return null;
        };
    }
}
