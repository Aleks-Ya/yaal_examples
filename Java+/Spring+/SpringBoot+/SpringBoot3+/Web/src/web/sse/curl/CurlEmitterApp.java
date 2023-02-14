package web.sse.curl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
class CurlEmitterApp {
    public static void main(String[] args) {
        SpringApplication.run(CurlEmitterApp.class, args);
    }

    @Controller
    static class EmitterController {
        private final ExecutorService executor = Executors.newSingleThreadExecutor();

        /**
         * Send default event ("message").
         * Test: curl http://localhost:8080/emitter?userId=john
         */
        @GetMapping("/emitter")
        public SseEmitter eventEmitter(@RequestParam String userId) {
            var emitter = new SseEmitter(12000L);
            executor.execute(() -> {
                try {
                    for (var i = 0; i < 4; i++) {
                        emitter.send("message" + i);
                    }
                    emitter.complete();
                } catch (Exception e) {
                    emitter.completeWithError(e);
                }
            });
            return emitter;
        }

        /**
         * Use {@link SseEmitter#event()} builder for event creation.
         * Test: curl http://localhost:8080/emitterBuilder?userId=john
         */
        @GetMapping("/emitterBuilder")
        public SseEmitter eventEmitterBuilder(@RequestParam String userId) {
            var emitter = new SseEmitter(12000L);
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
