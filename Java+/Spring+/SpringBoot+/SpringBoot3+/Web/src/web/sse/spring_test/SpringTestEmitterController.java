package web.sse.spring_test;

import jakarta.annotation.PreDestroy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
class SpringTestEmitterController {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private SseEmitter emitter;
    private int counter = 0;

    @GetMapping("/emitter")
    public SseEmitter eventEmitterBuilder() {
        emitter = new SseEmitter(12000L);
        return emitter;
    }

    @Scheduled(fixedDelay = 1000)
    public void submit() {
        executor.execute(() -> {
            try {
                var event = SseEmitter.event()
                        .id("id" + counter)
                        .name("eventName" + counter)
                        .comment("comment" + counter)
                        .reconnectTime(5000 + counter)
                        .data("message" + counter).build();
                emitter.send(event);
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        counter++;
    }

    @PreDestroy
    void shutdown() {
        executor.shutdown();
    }

}
