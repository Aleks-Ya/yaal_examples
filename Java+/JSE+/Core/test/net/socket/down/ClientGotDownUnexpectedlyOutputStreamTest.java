package net.socket.down;

import org.junit.jupiter.api.Disabled;
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
 * Client gets down and Server gets {@link SocketException} "Connection reset by peer (Write failed)"
 * while writing to OutputStream.
 */
@Disabled("stuck, need fix")
class ClientGotDownUnexpectedlyOutputStreamTest {
    private static final String BODY = "abc";
    private static final int PORT = 2513;
    private static final int SERVER_TIMEOUT = 1000;
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
            assertThat(cause.getMessage()).isEqualTo("Connection reset by peer (Write failed)");
            clientFuture.get();
            executor.shutdown();
            return;
        }
        throw new AssertionError("Expected ExecutionException");
    }

    private Callable<Void> serverCallable() {
        return () -> {
            try (var serverSocket = new ServerSocket(PORT)) {
                serverSocket.setSoTimeout(SERVER_TIMEOUT);
                latch.countDown();
                System.out.println("Server is waiting for client");
                var clientSocket = serverSocket.accept();
                System.out.println("Server accepted");
                var pw = clientSocket.getOutputStream();
                while (true) {
                    pw.write(BODY.getBytes());
                }
            }
        };
    }

    private Callable<Void> clientCallable() {
        return () -> {
            System.out.println("Client is waiting Server");
            latch.await();
            var sb = new StringBuilder();
            var socket = new Socket("localhost", PORT);
            var is = socket.getInputStream();
            int i;
            while ((i = is.read()) != -1) {
                sb.append((char) i);
                System.out.println("sb size " + sb.length());
                if (sb.length() > 10) {
                    System.out.println("Client got down unexpectedly");
                    return null;
                }
            }
            return null;
        };
    }
}
