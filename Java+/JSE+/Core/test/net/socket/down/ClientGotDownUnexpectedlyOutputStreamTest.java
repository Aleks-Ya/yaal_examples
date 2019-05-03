package net.socket.down;

import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.*;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Client gets down and Server gets {@link SocketException} "Connection reset by peer (Write failed)"
 * while writing to OutputStream.
 */
public class ClientGotDownUnexpectedlyOutputStreamTest {
    private static final String BODY = "abc";
    private static final int PORT = 2512;
    private static final int SERVER_TIMEOUT = 1000;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Test
    public void clientGotDown() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Void> serverFuture = executor.submit(serverCallable());
        Future<Void> clientFuture = executor.submit(clientCallable());
        try {
            serverFuture.get();
        } catch (ExecutionException e) {
            System.out.println("Server got SocketException");
            SocketException cause = (SocketException) e.getCause();
            assertThat(cause.getMessage(), equalTo("Connection reset by peer (Write failed)"));
            clientFuture.get();
            executor.shutdown();
            return;
        }
        throw new AssertionError("Expected ExecutionException");
    }

    private Callable<Void> serverCallable() {
        return () -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                serverSocket.setSoTimeout(SERVER_TIMEOUT);
                latch.countDown();
                System.out.println("Server is waiting for client");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server accepted");
                OutputStream pw = clientSocket.getOutputStream();
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
            StringBuilder sb = new StringBuilder();
            Socket socket = new Socket("localhost", PORT);
            InputStream is = socket.getInputStream();
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
