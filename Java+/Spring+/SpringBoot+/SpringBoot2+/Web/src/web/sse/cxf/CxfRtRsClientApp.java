package web.sse.cxf;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.sse.InboundSseEvent;
import javax.ws.rs.sse.SseEventSource;
import java.util.function.Consumer;

public class CxfRtRsClientApp {
    public static void main(String[] args) throws InterruptedException {
        var client = ClientBuilder.newClient();
        var target = client.target("http://localhost:8080/emitter");
        try (var source = SseEventSource.target(target).build()) {
            var onMessage = (Consumer<InboundSseEvent>) event -> System.out.println(event.readData());
            var onError = (Consumer<Throwable>) Throwable::printStackTrace;
            var onComplete = (Runnable) () -> System.out.println("Completed");
            source.register(onMessage, onError, onComplete);
            source.open();
            Thread.sleep(5000);
        }
    }
}
