package gptui.ui;

import gptui.storage.Interaction;
import org.junit.jupiter.api.Test;

import static gptui.storage.AnswerState.FAIL;
import static gptui.storage.AnswerType.GCP;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;
import static gptui.ui.TestingData.INTERACTION_1;
import static gptui.ui.TestingData.INTERACTION_1_GCP_HTML;
import static gptui.ui.TestingData.INTERACTION_1_GRAMMAR_HTML;
import static gptui.ui.TestingData.INTERACTION_1_LONG_HTML;
import static gptui.ui.TestingData.INTERACTION_1_QUESTION;
import static gptui.ui.TestingData.INTERACTION_1_SHORT_HTML;
import static gptui.ui.TestingData.INTERACTION_1_THEME;
import static gptui.ui.TestingData.INTERACTION_2_GCP_HTML;
import static gptui.ui.TestingData.INTERACTION_2_GRAMMAR_HTML;
import static gptui.ui.TestingData.INTERACTION_2_LONG_HTML;
import static gptui.ui.TestingData.INTERACTION_2_SHORT_HTML;
import static java.time.Duration.ZERO;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

class RegenerateQuestionTest extends BaseGptUiTest {
    private static final String EXP_GRAMMAR_HTML_BODY_2 = wrapExpectedWebViewContent(INTERACTION_2_GRAMMAR_HTML);
    private static final String EXP_SHORT_HTML_BODY_2 = wrapExpectedWebViewContent(INTERACTION_2_SHORT_HTML);
    private static final String EXP_LONG_HTML_BODY_2 = wrapExpectedWebViewContent(INTERACTION_2_LONG_HTML);
    private static final String EXP_GCP_HTML_BODY_2 = wrapExpectedWebViewContent(INTERACTION_2_GCP_HTML);
    private final Interaction interaction1 = INTERACTION_1
            .withAnswer(SHORT, answer -> answer.withState(FAIL))
            .withAnswer(LONG, answer -> answer.withState(FAIL))
            .withAnswer(GCP, answer -> answer.withState(FAIL));

    @Override
    public void init() {
        storage.saveInteraction(interaction1);
    }

    @Test
    void currentInteractionIsTheOnly() {
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(interaction1)
                .historyItems(interaction1)
                .themeSize(1)
                .themeSelectedItem(INTERACTION_1_THEME)
                .themeItems(INTERACTION_1_THEME)
                .questionText(INTERACTION_1_QUESTION)
                .modelEditedQuestion(INTERACTION_1_QUESTION)
                .answerGrammarText(INTERACTION_1_GRAMMAR_HTML)
                .answerShortText(INTERACTION_1_SHORT_HTML)
                .answerLongText(INTERACTION_1_LONG_HTML)
                .answerGcpText(INTERACTION_1_GCP_HTML)
                .answerCircleColors(GREEN, RED, RED, RED)
                .assertApp();

        gptApi.clear()
                .putGrammarResponse(INTERACTION_2_GRAMMAR_HTML, ZERO)
                .putShortResponse(INTERACTION_2_SHORT_HTML, ZERO)
                .putLongResponse(INTERACTION_2_LONG_HTML, ZERO)
                .putGcpResponse(INTERACTION_2_GCP_HTML, ZERO);
        clickOn(getRegenerateButton());

        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(interaction1.id()).orElseThrow())
                .historyItems(storage.readInteraction(interaction1.id()).orElseThrow())
                .themeSize(1)
                .themeSelectedItem(INTERACTION_1_THEME)
                .themeItems(INTERACTION_1_THEME)
                .questionText(INTERACTION_1_QUESTION)
                .modelEditedQuestion(INTERACTION_1_QUESTION)
                .answerGrammarText(EXP_GRAMMAR_HTML_BODY_2)
                .answerShortText(EXP_SHORT_HTML_BODY_2)
                .answerLongText(EXP_LONG_HTML_BODY_2)
                .answerGcpText(EXP_GCP_HTML_BODY_2)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .assertApp();
    }
}