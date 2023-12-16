package gptui.ui;

import gptui.storage.Answer;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import gptui.storage.InteractionType;

import java.util.Map;

import static gptui.storage.AnswerState.FAIL;
import static gptui.storage.AnswerState.SUCCESS;
import static gptui.storage.AnswerType.GCP;
import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;

public class TestingData {

    public static class I1 {
        public static final String THEME = "Theme 1";
        public static final String QUESTION = "Question 1";
        public static final String GRAMMAR_HTML = "Grammar answer HTML 1";
        public static final String SHORT_HTML = "Short answer HTML 1";
        public static final String LONG_HTML = "Long answer HTML 1";
        public static final String GCP_HTML = "GCP answer HTML 1";
        public static final String EXP_GRAMMAR_HTML_BODY = wrapExpectedWebViewContent(GRAMMAR_HTML);
        public static final String EXP_SHORT_HTML_BODY = wrapExpectedWebViewContent(SHORT_HTML);
        public static final String EXP_LONG_HTML_BODY = wrapExpectedWebViewContent(LONG_HTML);
        public static final String EXP_GCP_HTML_BODY = wrapExpectedWebViewContent(GCP_HTML);
        public static final Interaction INTERACTION = new Interaction(new InteractionId(1L), InteractionType.QUESTION, THEME, QUESTION, Map.of(
                GRAMMAR, new Answer(GRAMMAR, "QC prompt 1", 50, "Grammar answer MD 1", GRAMMAR_HTML, SUCCESS),
                SHORT, new Answer(SHORT, "Short prompt 1", 60, "Short answer MD 1", SHORT_HTML, SUCCESS),
                LONG, new Answer(LONG, "Long prompt 1", 70, "Long answer MD 1", LONG_HTML, SUCCESS),
                GCP, new Answer(GCP, "GCP prompt 1", 80, "GCP answer MD 1", GCP_HTML, SUCCESS)));
    }

    public static class I2 {
        public static final String THEME = "Theme 2";
        public static final String QUESTION = "Question 2";
        public static final String GRAMMAR_HTML = "Grammar answer HTML 2";
        public static final String SHORT_HTML = "Short answer HTML 2";
        public static final String LONG_HTML = "Long answer HTML 2";
        public static final String GCP_HTML = "GCP answer HTML 2";
        public static final String EXP_GRAMMAR_HTML_BODY = wrapExpectedWebViewContent(I2.GRAMMAR_HTML);
        public static final String EXP_SHORT_HTML_BODY = wrapExpectedWebViewContent(I2.SHORT_HTML);
        public static final String EXP_LONG_HTML_BODY = wrapExpectedWebViewContent(I2.LONG_HTML);
        public static final String EXP_GCP_HTML_BODY = wrapExpectedWebViewContent(I2.GCP_HTML);
        public static final Interaction INTERACTION = new Interaction(new InteractionId(2L), InteractionType.QUESTION, THEME, QUESTION, Map.of(
                GRAMMAR, new Answer(GRAMMAR, "QC prompt 2", 50, "Grammar answer MD 2", I2.GRAMMAR_HTML, SUCCESS),
                SHORT, new Answer(SHORT, "Short prompt 2", 60, "Short answer MD 2", I2.SHORT_HTML, SUCCESS),
                LONG, new Answer(LONG, "Long prompt 2", 70, "Long answer MD 2", I2.LONG_HTML, FAIL),
                GCP, new Answer(GCP, "GCP prompt 2", 80, "GCP answer MD 2", I2.GCP_HTML, SUCCESS)));
    }

    public static class I3 {
        public static final String THEME = "Theme 3";
        public static final String QUESTION = "Question 3";
        public static final String GRAMMAR_HTML = "Grammar answer HTML 3";
        public static final String SHORT_HTML = "Short answer HTML 3";
        public static final String LONG_HTML = "Long answer HTML 3";
        public static final String GCP_HTML = "GCP answer HTML 3";
        public static final Interaction INTERACTION = new Interaction(new InteractionId(3L), InteractionType.QUESTION, THEME, QUESTION, Map.of(
                GRAMMAR, new Answer(GRAMMAR, "QC prompt 3", 50, "Grammar answer MD 3", I3.GRAMMAR_HTML, SUCCESS),
                SHORT, new Answer(SHORT, "Short prompt 3", 60, "Short answer MD 3", I3.SHORT_HTML, SUCCESS),
                LONG, new Answer(LONG, "Long prompt 3", 70, "Long answer MD 3", I3.LONG_HTML, FAIL),
                GCP, new Answer(GCP, "GCP prompt 3", 80, "GCP answer MD 3", I3.GCP_HTML, SUCCESS)));
    }

    private static String wrapExpectedWebViewContent(String text) {
        return "<p>" + text + "</p>\n";
    }
}
