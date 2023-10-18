package gptui.ui;

import gptui.storage.Answer;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;

import java.util.Map;

import static gptui.storage.AnswerState.FAIL;
import static gptui.storage.AnswerState.SUCCESS;
import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;
import static gptui.storage.InteractionType.QUESTION;

public class TestingData {
    public static final Interaction INTERACTION_1 = new Interaction(new InteractionId(1L), QUESTION, "Theme 1", "Question 1", Map.of(
            GRAMMAR,
            new Answer(GRAMMAR, "QC prompt 1", "Grammar answer MD 1", "Grammar answer HTML 1", SUCCESS),
            SHORT,
            new Answer(SHORT, "Short prompt 1", "Short answer MD 1", "Short answer HTML 1", SUCCESS),
            LONG,
            new Answer(LONG, "Long prompt 1", "Long answer MD 1", "Long answer HTML 1", SUCCESS)
    ));
    public static final Interaction INTERACTION_2 = new Interaction(new InteractionId(2L), QUESTION, "Theme 2", "Question 2", Map.of(
            GRAMMAR,
            new Answer(GRAMMAR, "QC prompt 2", "Grammar answer MD 2", "Grammar answer HTML 2", SUCCESS),
            SHORT,
            new Answer(SHORT, "Short prompt 2", "Short answer MD 2", "Short answer HTML 2", SUCCESS),
            LONG,
            new Answer(LONG, "Long prompt 2", "Long answer MD 2", "Long answer HTML 2", FAIL)
    ));
    public static final Interaction INTERACTION_3 = new Interaction(new InteractionId(3L), QUESTION, "Theme 3", "Question 3", Map.of(
            GRAMMAR,
            new Answer(GRAMMAR, "QC prompt 3", "Grammar answer MD 3", "Grammar answer HTML 3", SUCCESS),
            SHORT,
            new Answer(SHORT, "Short prompt 3", "Short answer MD 3", "Short answer HTML 3", SUCCESS),
            LONG,
            new Answer(LONG, "Long prompt 3", "Long answer MD 3", "Long answer HTML 3", FAIL)
    ));
}
