package web.sse.javascript;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class JavaScriptEmitterApp {
    public static void main(String[] args) {
        SpringApplication.run(JavaScriptEmitterApp.class, args);
    }

    @Controller
    public static class EmitterController {
        private final ExecutorService executor = Executors.newSingleThreadExecutor();
        private SseEmitter emitter;

        @GetMapping("/emitter")
        public SseEmitter eventEmitterBuilder() {
            emitter = new SseEmitter(12000L);
            emitter.onCompletion(() -> System.out.println("Completed"));
            emitter.onError((t) -> System.out.println("Error: " + t.getMessage()));
            emitter.onTimeout(() -> System.out.println("Timeout"));
            executor.execute(() -> {
                try {
                    for (var i = 0; i < 4; i++) {
                        var event = SseEmitter.event()
                                .id("id" + i)
                                .name("eventName" + i)
                                .comment("comment" + i)
                                .reconnectTime(5000 + i)
                                .data("message" + i).build();
                        emitter.send(event);
                    }
                } catch (Exception e) {
                    emitter.completeWithError(e);
                }
            });
            return emitter;
        }

        @GetMapping("/stop")
        @ResponseStatus(HttpStatus.OK)
        public void stopEmitter() throws IOException {
            if (emitter != null) {
                System.out.println("Closing the emitter...");
                emitter.send(SseEmitter.event().name("close").build());
                System.out.println("Emitter is closed.");
            }
        }

        @PreDestroy
        void shutdown() {
            executor.shutdown();
        }

    }
}
