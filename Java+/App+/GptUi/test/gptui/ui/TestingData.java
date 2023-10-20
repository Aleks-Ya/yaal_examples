package gptui.ui;

import gptui.storage.Answer;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;

import java.util.Map;

import static gptui.storage.AnswerState.FAIL;
import static gptui.storage.AnswerState.SUCCESS;
import static gptui.storage.AnswerType.GCP;
import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;
import static gptui.storage.InteractionType.QUESTION;

public class TestingData {
    public static final String INTERACTION_1_THEME = "Theme 1";
    public static final String INTERACTION_1_QUESTION = "Question 1";
    public static final String INTERACTION_1_GRAMMAR_HTML = "Grammar answer HTML 1";
    public static final String INTERACTION_1_SHORT_HTML = "Short answer HTML 1";
    public static final String INTERACTION_1_LONG_HTML = "Long answer HTML 1";
    public static final String INTERACTION_1_GCP_HTML = "GCP answer HTML 1";
    public static final Interaction INTERACTION_1 = new Interaction(
            new InteractionId(1L), QUESTION, INTERACTION_1_THEME, INTERACTION_1_QUESTION, Map.of(
            GRAMMAR,
            new Answer(GRAMMAR, "QC prompt 1", "Grammar answer MD 1", INTERACTION_1_GRAMMAR_HTML, SUCCESS),
            SHORT,
            new Answer(SHORT, "Short prompt 1", "Short answer MD 1", INTERACTION_1_SHORT_HTML, SUCCESS),
            LONG,
            new Answer(LONG, "Long prompt 1", "Long answer MD 1", INTERACTION_1_LONG_HTML, SUCCESS),
            GCP,
            new Answer(GCP, "GCP prompt 1", "GCP answer MD 1", INTERACTION_1_GCP_HTML, SUCCESS)
    ));

    public static final String INTERACTION_2_THEME = "Theme 2";
    public static final String INTERACTION_2_QUESTION = "Question 2";
    public static final String INTERACTION_2_GRAMMAR_HTML = "Grammar answer HTML 2";
    public static final String INTERACTION_2_SHORT_HTML = "Short answer HTML 2";
    public static final String INTERACTION_2_LONG_HTML = "Long answer HTML 2";
    public static final String INTERACTION_2_GCP_HTML = "GCP answer HTML 2";
    public static final Interaction INTERACTION_2 = new Interaction(
            new InteractionId(2L), QUESTION, INTERACTION_2_THEME, INTERACTION_2_QUESTION, Map.of(
            GRAMMAR,
            new Answer(GRAMMAR, "QC prompt 2", "Grammar answer MD 2", INTERACTION_2_GRAMMAR_HTML, SUCCESS),
            SHORT,
            new Answer(SHORT, "Short prompt 2", "Short answer MD 2", INTERACTION_2_SHORT_HTML, SUCCESS),
            LONG,
            new Answer(LONG, "Long prompt 2", "Long answer MD 2", INTERACTION_2_LONG_HTML, FAIL),
            GCP,
            new Answer(GCP, "GCP prompt 2", "GCP answer MD 2", INTERACTION_2_GCP_HTML, SUCCESS)
    ));

    public static final String INTERACTION_3_THEME = "Theme 3";
    public static final String INTERACTION_3_QUESTION = "Question 3";
    public static final String INTERACTION_3_GRAMMAR_HTML = "Grammar answer HTML 3";
    public static final String INTERACTION_3_SHORT_HTML = "Short answer HTML 3";
    public static final String INTERACTION_3_LONG_HTML = "Long answer HTML 3";
    public static final String INTERACTION_3_GCP_HTML = "GCP answer HTML 3";
    public static final Interaction INTERACTION_3 = new Interaction(
            new InteractionId(3L), QUESTION, INTERACTION_3_THEME, INTERACTION_3_QUESTION, Map.of(
            GRAMMAR,
            new Answer(GRAMMAR, "QC prompt 3", "Grammar answer MD 3", INTERACTION_3_GRAMMAR_HTML, SUCCESS),
            SHORT,
            new Answer(SHORT, "Short prompt 3", "Short answer MD 3", INTERACTION_3_SHORT_HTML, SUCCESS),
            LONG,
            new Answer(LONG, "Long prompt 3", "Long answer MD 3", INTERACTION_3_LONG_HTML, FAIL),
            GCP,
            new Answer(GCP, "GCP prompt 3", "GCP answer MD 3", INTERACTION_3_GCP_HTML, SUCCESS)
    ));
}
