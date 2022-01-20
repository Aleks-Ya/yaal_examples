package web.sse.okhttp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class OkHttpEmitterApp {
    public static void main(String[] args) {
        SpringApplication.run(OkHttpEmitterApp.class, args);
    }

    @Controller
    public static class EmitterController {
        private final ExecutorService executor = Executors.newSingleThreadExecutor();

        @GetMapping("/emitterOkHttp")
        public SseEmitter eventEmitterBuilder() {
            SseEmitter emitter = new SseEmitter(12000L);
            executor.execute(() -> {
                try {
                    for (int i = 0; i < 4; i++) {
                        var event = SseEmitter.event()
                                .id("id" + i)
                                .name("eventName" + i)
                                .comment("comment" + i)
                                .reconnectTime(5000 + i)
                                .data("message" + i).build();
                        emitter.send(event);
                    }
                    emitter.complete();
                } catch (Exception e) {
                    emitter.completeWithError(e);
                }
            });
            return emitter;
        }

        @PreDestroy
        void shutdown() {
            executor.shutdown();
        }

    }
}
