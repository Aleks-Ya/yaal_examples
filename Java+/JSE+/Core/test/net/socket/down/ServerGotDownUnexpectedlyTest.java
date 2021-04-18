package net.socket.down;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Server gets down and Client gets {@link SocketTimeoutException}.
 */
public class ServerGotDownUnexpectedlyTest {
    private static final String BODY = "abc";
    private static final int PORT = 2512;
    private static final int CLIENT_TIMEOUT = 1000;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Test
    public void serverGotDown() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Void> serverFuture = executor.submit(serverCallable());
        Future<Void> clientFuture = executor.submit(clientCallable());
        serverFuture.get();
        clientFuture.get();
        executor.shutdown();
    }

    private Callable<Void> serverCallable() {
        return () -> {
            ServerSocket serverSocket = new ServerSocket(PORT);
            latch.countDown();
            System.out.println("Server is waiting for client");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Server accepted");
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
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
            StringBuilder sb = new StringBuilder();
            try {
                Socket socket = new Socket("localhost", PORT);
                socket.setSoTimeout(CLIENT_TIMEOUT);
                InputStream is = socket.getInputStream();
                int i;
                while ((i = is.read()) != -1) {
                    sb.append((char) i);
                }
            } catch (SocketTimeoutException e) {
                System.out.println("Client got SocketTimeoutException");
                assertThat(sb.toString(), equalTo(BODY));
                return null;
            }
            throw new AssertionError("SocketTimeoutException is expected");
        };
    }
}
