package web.sse.javascript;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@EnableScheduling
@SpringBootApplication
public class JavaScriptEmitterApp {
    private static final Logger log = LoggerFactory.getLogger(JavaScriptEmitterApp.class);

    public static void main(String[] args) {
        SpringApplication.run(JavaScriptEmitterApp.class, args);
    }

    @Controller
    public static class EmitterController {
        private static final long TIMEOUT = 12000L;
        private final ExecutorService executor = Executors.newSingleThreadExecutor();
        private final AtomicInteger counter = new AtomicInteger();
        private SseEmitter emitter;

        @GetMapping("/createEmitter")
        public SseEmitter createEmitter() {
            log.info("Creating SseEmitter...");
            emitter = new SseEmitter(TIMEOUT);
            emitter.onCompletion(() -> log.info("Callback onCompletion."));
            emitter.onError((t) -> log.error("Callback onError.", t));
            emitter.onTimeout(() -> log.info("Callback onTimeout."));
            log.info("SseEmitter is created.");
            return emitter;
        }

        @GetMapping("/emit")
        @ResponseStatus(HttpStatus.OK)
        public void emit() {
            log.info("Scheduling emitting...");
            executor.execute(() -> {
                log.info("Emitting...");
                try {
                    var count = counter.getAndIncrement();
                    var event = SseEmitter.event()
                            .id("id" + count)
                            .name("eventName" + count)
                            .comment("comment" + count)
                            .reconnectTime(5000)
                            .data("message" + count).build();
                    emitter.send(event);
                } catch (Exception e) {
                    log.error("CompleteWithError.", e);
                    emitter.completeWithError(e);
                }
                log.info("Emitted.");
            });
            log.info("Emitting is scheduled.");
        }

        //        @Scheduled(fixedRate = TIMEOUT / 2)
        public void keepAlive() throws IOException {
            if (emitter != null) {
                log.info("Sending keep alive event...");
                emitter.send(SseEmitter.event().id("keep-alive-id").comment("KeepAlive").build());
                log.info("Keep alive event is sent.");
            } else {
                log.info("Skip sending Keep Alive event (SseEmitter is null).");
            }
        }

        @GetMapping("/stopEmitter")
        @ResponseStatus(HttpStatus.OK)
        public void stopEmitter() throws IOException {
            log.info("Stopping SseEmitter...");
            if (emitter != null) {
                emitter.send(SseEmitter.event().name("close").build());
            }
            log.info("SseEmitter is stopped.");
        }

        @PreDestroy
        void shutdown() {
            log.info("Shutting ExecutorService down...");
            executor.shutdown();
            log.info("ExecutorService is down.");
        }

    }
}
