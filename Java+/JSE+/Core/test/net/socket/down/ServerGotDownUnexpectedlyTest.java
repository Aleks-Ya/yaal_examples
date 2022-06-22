package net.socket.down;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Server gets down and Client gets {@link SocketTimeoutException}.
 */
class ServerGotDownUnexpectedlyTest {
    private static final String BODY = "abc";
    private static final int PORT = 2515;
    private static final int CLIENT_TIMEOUT = 1000;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Test
    void serverGotDown() throws InterruptedException, ExecutionException {
        var executor = Executors.newFixedThreadPool(2);
        var serverFuture = executor.submit(serverCallable());
        var clientFuture = executor.submit(clientCallable());
        serverFuture.get();
        clientFuture.get();
        executor.shutdown();
    }

    private Callable<Void> serverCallable() {
        return () -> {
            var serverSocket = new ServerSocket(PORT);
            latch.countDown();
            System.out.println("Server is waiting for client");
            var clientSocket = serverSocket.accept();
            System.out.println("Server accepted");
            var pw = new PrintWriter(clientSocket.getOutputStream());
            pw.write(BODY);
            pw.flush();
            System.out.println("Server has fall unexpectedly");
            return null;
        };
    }

    private Callable<Void> clientCallable() {
        return () -> {
            System.out.println("Client is waiting Server");
            latch.await();
            var sb = new StringBuilder();
            try {
                var socket = new Socket("localhost", PORT);
                socket.setSoTimeout(CLIENT_TIMEOUT);
                var is = socket.getInputStream();
                int i;
                while ((i = is.read()) != -1) {
                    sb.append((char) i);
                }
            } catch (SocketTimeoutException e) {
                System.out.println("Client got SocketTimeoutException");
                assertThat(sb.toString()).isEqualTo(BODY);
                return null;
            }
            throw new AssertionError("SocketTimeoutException is expected");
        };
    }
}
