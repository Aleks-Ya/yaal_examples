package gptui;

import gptui.storage.InteractionId;
import org.slf4j.MDC;

import java.util.concurrent.Callable;

public class Mdc {
    private static final String INTERACTION_ID_MDC = "interactionId";

    public static <T> T call(InteractionId interactionId, Callable<T> callable) {
        try {
            MDC.put(INTERACTION_ID_MDC, interactionId.id().toString() + " ");
            T result = callable.call();
            MDC.clear();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void run(InteractionId interactionId, Runnable runnable) {
        MDC.put(INTERACTION_ID_MDC, interactionId.id().toString() + " ");
        runnable.run();
        MDC.clear();
    }
}
