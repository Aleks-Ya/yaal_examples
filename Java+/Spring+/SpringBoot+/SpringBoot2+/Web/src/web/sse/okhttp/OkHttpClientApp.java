package web.sse.okhttp;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.MessageEvent;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;

import static java.lang.String.format;

public class OkHttpClientApp {
    public static void main(String[] args) throws InterruptedException {
        var eventHistory = new ArrayList<String>();
        var eventHandler = new EventHandler() {

            @Override
            public void onOpen() {
                eventHistory.add("onOpen");
            }

            @Override
            public void onClosed() {
                eventHistory.add("onClosed");
            }

            @Override
            public void onMessage(String event, MessageEvent messageEvent) {
                eventHistory.add(format("onMessage: event='%s', messageEvent='%s'", event, messageEvent));
            }

            @Override
            public void onComment(String comment)  {
                eventHistory.add(format("onComment: comment='%s'", comment));
            }

            @Override
            public void onError(Throwable t) {
                eventHistory.add(format("onError: throwable='%s'", t));
            }
        };
        try (EventSource es = new EventSource.Builder(eventHandler, URI.create("http://localhost:8080/emitterOkHttp"))
                .build()) {
            es.start();
            es.awaitClosed(Duration.ofSeconds(5));
        }
        System.out.println(eventHistory);
//        assert eventHistory.equals(List.of("onOpen", "onClosed"));
    }
}
