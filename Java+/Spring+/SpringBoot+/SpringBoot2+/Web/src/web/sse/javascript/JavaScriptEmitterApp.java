package web.sse.javascript;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import util.ResourceUtil;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Open http://localhost:8080/page
 */
@SpringBootApplication
public class JavaScriptEmitterApp {
    public static void main(String[] args) {
        SpringApplication.run(JavaScriptEmitterApp.class, args);
    }

    @Controller
    public static class EmitterController {
        private final ExecutorService executor = Executors.newSingleThreadExecutor();

        @GetMapping("/page")
        @ResponseBody
        public String page() {
            return ResourceUtil.resourceToString(JavaScriptEmitterApp.class, "page.html");
        }

        @GetMapping("/emitter")
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
                    emitter.send(SseEmitter.event().name("close").build());
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
