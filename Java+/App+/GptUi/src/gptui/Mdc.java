package gptui;

import gptui.storage.InteractionId;
import org.slf4j.MDC;

public class Mdc {
    private static final String INTERACTION_ID_MDC = "interactionId";

    public static void run(InteractionId interactionId, Runnable runnable) {
        MDC.put(INTERACTION_ID_MDC, interactionId.id().toString() + " ");
        runnable.run();
        MDC.clear();
    }
}
