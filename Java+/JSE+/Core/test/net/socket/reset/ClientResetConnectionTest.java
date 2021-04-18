package net.socket.reset;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Client resets the connection (sends RST flag to Server).
 */
public class ClientResetConnectionTest {
    private static final int PORT = 2512;
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
                latch.countDown();
                System.out.println("Server is waiting for client");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server accepted");
                OutputStream pw = clientSocket.getOutputStream();
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
            StringBuilder sb = new StringBuilder();
            Socket socket = new Socket("localhost", PORT);
            InputStream is = socket.getInputStream();
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
