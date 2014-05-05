import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class SocketProcessor implements Runnable {
    private Socket socket;
    private static final Executor executor = Executors.newFixedThreadPool(10);

    public static void process(Socket socket) {
        SocketProcessor processor = new SocketProcessor();
        processor.socket = socket;
        executor.execute(processor);
    }

    private SocketProcessor() {
    }

    @Override
    public void run() {
        try {
            BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
            System.out.println(is.available());
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
