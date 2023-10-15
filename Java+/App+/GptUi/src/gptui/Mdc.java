package gptui;

import gptui.storage.AnswerType;
import gptui.storage.InteractionId;
import org.slf4j.MDC;

public class Mdc {
    private static final String INTERACTION_ID_MDC = "interactionId";
    private static final String ANSWER_TYPE_MDC = "answerType";

    public static void run(InteractionId interactionId, Runnable runnable) {
        MDC.put(INTERACTION_ID_MDC, interactionId.id().toString() + " ");
        runnable.run();
        MDC.remove(INTERACTION_ID_MDC);
    }

    public static void run(AnswerType answerType, Runnable runnable) {
        MDC.put(ANSWER_TYPE_MDC, " " + answerType);
        runnable.run();
        MDC.remove(ANSWER_TYPE_MDC);
    }
}
