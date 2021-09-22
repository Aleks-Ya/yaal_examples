package net.socket.down;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * Client gets down and Server gets error status in {@link PrintWriter#checkError()}
 * while writing to PrintWriter.
 */
@Disabled("stuck, need fix")
class ClientGotDownUnexpectedlyPrintWriterTest {
    private static final String BODY = "abc";
    private static final int PORT = 2514;
    private static final int SERVER_TIMEOUT = 1000;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Test
    void clientGotDown() throws InterruptedException, ExecutionException {
        var executor = Executors.newFixedThreadPool(2);
        var serverFuture = executor.submit(serverCallable());
        var clientFuture = executor.submit(clientCallable());
        serverFuture.get();
        clientFuture.get();
        executor.shutdown();
    }

    private Callable<Void> serverCallable() {
        return () -> {
            try (var serverSocket = new ServerSocket(PORT)) {
                serverSocket.setSoTimeout(SERVER_TIMEOUT);
                latch.countDown();
                System.out.println("Server is waiting for client");
                var clientSocket = serverSocket.accept();
                System.out.println("Server accepted");
                var pw = new PrintWriter(clientSocket.getOutputStream());
                while (!pw.checkError()) {
                    pw.write(BODY);
                    pw.flush();
                }
                System.out.println("Server found an error");
                return null;
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
