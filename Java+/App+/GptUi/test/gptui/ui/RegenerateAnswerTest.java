package gptui.ui;

import org.junit.jupiter.api.Test;

import static gptui.ui.TestingData.EXP_GCP_HTML_BODY_2;
import static gptui.ui.TestingData.EXP_GRAMMAR_HTML_BODY_2;
import static gptui.ui.TestingData.EXP_LONG_HTML_BODY_2;
import static gptui.ui.TestingData.EXP_SHORT_HTML_BODY_2;
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

class RegenerateAnswerTest extends BaseGptUiTest {
    public void init() {
        storage.saveInteraction(INTERACTION_1);
    }

    @Test
    void currentInteractionIsTheOnly() {
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(INTERACTION_1)
                .historyItems(INTERACTION_1)
                .themeSize(1)
                .themeSelectedItem(INTERACTION_1_THEME)
                .themeItems(INTERACTION_1_THEME)
                .questionText(INTERACTION_1_QUESTION)
                .modelEditedQuestion(INTERACTION_1_QUESTION)
                .answerGrammarText(INTERACTION_1_GRAMMAR_HTML)
                .answerShortText(INTERACTION_1_SHORT_HTML)
                .answerLongText(INTERACTION_1_LONG_HTML)
                .answerGcpText(INTERACTION_1_GCP_HTML)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTemperatures(0.5, 0.6, 0.7, 0.8)
                .assertApp();

        regenerateGrammarAnswer();
        regenerateShortAnswer();
        regenerateLongAnswer();
        regenerateGcpAnswer();
    }

    private void regenerateGrammarAnswer() {
        gptApi.clear().putGrammarResponse(INTERACTION_2_GRAMMAR_HTML, ZERO);
        clickOn(getAnswerGrammarRegenerateButton());
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(INTERACTION_1.id()).orElseThrow())
                .historyItems(storage.readInteraction(INTERACTION_1.id()).orElseThrow())
                .themeSize(1)
                .themeSelectedItem(INTERACTION_1_THEME)
                .themeItems(INTERACTION_1_THEME)
                .questionText(INTERACTION_1_QUESTION)
                .modelEditedQuestion(INTERACTION_1_QUESTION)
                .answerGrammarText(EXP_GRAMMAR_HTML_BODY_2)
                .answerShortText(INTERACTION_1_SHORT_HTML)
                .answerLongText(INTERACTION_1_LONG_HTML)
                .answerGcpText(INTERACTION_1_GCP_HTML)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTemperatures(0.5, 0.6, 0.7, 0.8)
                .assertApp();
    }

    private void regenerateShortAnswer() {
        gptApi.clear().putShortResponse(INTERACTION_2_SHORT_HTML, ZERO);
        clickOn(getAnswerShortRegenerateButton());
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(INTERACTION_1.id()).orElseThrow())
                .historyItems(storage.readInteraction(INTERACTION_1.id()).orElseThrow())
                .themeSize(1)
                .themeSelectedItem(INTERACTION_1_THEME)
                .themeItems(INTERACTION_1_THEME)
                .questionText(INTERACTION_1_QUESTION)
                .modelEditedQuestion(INTERACTION_1_QUESTION)
                .answerGrammarText(EXP_GRAMMAR_HTML_BODY_2)
                .answerShortText(EXP_SHORT_HTML_BODY_2)
                .answerLongText(INTERACTION_1_LONG_HTML)
                .answerGcpText(INTERACTION_1_GCP_HTML)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTemperatures(0.5, 0.6, 0.7, 0.8)
                .assertApp();
    }

    private void regenerateLongAnswer() {
        gptApi.clear().putLongResponse(INTERACTION_2_LONG_HTML, ZERO);
        clickOn(getAnswerLongRegenerateButton());
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(INTERACTION_1.id()).orElseThrow())
                .historyItems(storage.readInteraction(INTERACTION_1.id()).orElseThrow())
                .themeSize(1)
                .themeSelectedItem(INTERACTION_1_THEME)
                .themeItems(INTERACTION_1_THEME)
                .questionText(INTERACTION_1_QUESTION)
                .modelEditedQuestion(INTERACTION_1_QUESTION)
                .answerGrammarText(EXP_GRAMMAR_HTML_BODY_2)
                .answerShortText(EXP_SHORT_HTML_BODY_2)
                .answerLongText(EXP_LONG_HTML_BODY_2)
                .answerGcpText(INTERACTION_1_GCP_HTML)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTemperatures(0.5, 0.6, 0.7, 0.8)
                .assertApp();
    }

    private void regenerateGcpAnswer() {
        gptApi.clear().putGcpResponse(INTERACTION_2_GCP_HTML, ZERO);
        clickOn(getAnswerGcpRegenerateButton());
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(INTERACTION_1.id()).orElseThrow())
                .historyItems(storage.readInteraction(INTERACTION_1.id()).orElseThrow())
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
                .answerTemperatures(0.5, 0.6, 0.7, 0.8)
                .assertApp();
    }
}