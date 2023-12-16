package gptui.ui.answer;

import gptui.ui.BaseGptUiTest;
import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import org.junit.jupiter.api.Test;

import static java.time.Duration.ZERO;
import static javafx.scene.paint.Color.GREEN;

class RegenerateAnswerTest extends BaseGptUiTest {
    public void init() {
        storage.saveInteraction(I1.INTERACTION);
    }

    @Test
    void currentInteractionIsTheOnly() {
        assertion()
                .historySize(1, 1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(I1.INTERACTION)
                .historyItems(I1.INTERACTION)
                .themeSize(1)
                .themeSelectedItem(I1.THEME)
                .themeItems(I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .modelEditedQuestion(I1.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I1.GRAMMAR_HTML)
                .shortA().text(I1.SHORT_HTML)
                .longA().text(I1.LONG_HTML)
                .gcpA().text(I1.GCP_HTML)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(50, 60, 70, 80)
                .assertApp();

        regenerateGrammarAnswer();
        regenerateShortAnswer();
        regenerateLongAnswer();
        regenerateGcpAnswer();
    }

    private void regenerateGrammarAnswer() {
        gptApi.clear().putGrammarResponse(I2.GRAMMAR_HTML, ZERO);
        clickOn(grammarAnswer().temperatureIncrementButton());
        assertion()
                .historySize(1, 1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(I1.INTERACTION)
                .historyItems(I1.INTERACTION)
                .themeSize(1)
                .themeSelectedItem(I1.THEME)
                .themeItems(I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .modelEditedQuestion(I1.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I1.GRAMMAR_HTML)
                .shortA().text(I1.SHORT_HTML)
                .longA().text(I1.LONG_HTML)
                .gcpA().text(I1.GCP_HTML)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(55, 60, 70, 80)
                .assertApp();

        clickOn(grammarAnswer().regenerateButton());
        gptApi.waitUntilSent(1);
        assertion()
                .historySize(1, 1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(I1.INTERACTION.id()).orElseThrow())
                .historyItems(storage.readInteraction(I1.INTERACTION.id()).orElseThrow())
                .themeSize(1)
                .themeSelectedItem(I1.THEME)
                .themeItems(I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .modelEditedQuestion(I1.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I2.EXP_GRAMMAR_HTML_BODY)
                .shortA().text(I1.SHORT_HTML)
                .longA().text(I1.LONG_HTML)
                .gcpA().text(I1.GCP_HTML)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperatures(55, 60, 70, 80)
                .answerSpinnerTemperatures(55, 60, 70, 80)
                .assertApp();
    }

    private void regenerateShortAnswer() {
        gptApi.clear().putShortResponse(I2.SHORT_HTML, ZERO);
        clickOn(shortAnswer().regenerateButton());
        gptApi.waitUntilSent(1);
        assertion()
                .historySize(1, 1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(I1.INTERACTION.id()).orElseThrow())
                .historyItems(storage.readInteraction(I1.INTERACTION.id()).orElseThrow())
                .themeSize(1)
                .themeSelectedItem(I1.THEME)
                .themeItems(I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .modelEditedQuestion(I1.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I2.EXP_GRAMMAR_HTML_BODY)
                .shortA().text(I2.EXP_SHORT_HTML_BODY)
                .longA().text(I1.LONG_HTML)
                .gcpA().text(I1.GCP_HTML)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperatures(55, 60, 70, 80)
                .answerSpinnerTemperatures(55, 60, 70, 80)
                .assertApp();
    }

    private void regenerateLongAnswer() {
        gptApi.clear().putLongResponse(I2.LONG_HTML, ZERO);
        clickOn(longAnswer().regenerateButton());
        gptApi.waitUntilSent(1);
        assertion()
                .historySize(1, 1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(I1.INTERACTION.id()).orElseThrow())
                .historyItems(storage.readInteraction(I1.INTERACTION.id()).orElseThrow())
                .themeSize(1)
                .themeSelectedItem(I1.THEME)
                .themeItems(I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .modelEditedQuestion(I1.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I2.EXP_GRAMMAR_HTML_BODY)
                .shortA().text(I2.EXP_SHORT_HTML_BODY)
                .longA().text(I2.EXP_LONG_HTML_BODY)
                .gcpA().text(I1.GCP_HTML)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperatures(55, 60, 70, 80)
                .answerSpinnerTemperatures(55, 60, 70, 80)
                .assertApp();
    }

    private void regenerateGcpAnswer() {
        gptApi.clear().putGcpResponse(I2.GCP_HTML, ZERO);
        clickOn(gcpAnswer().regenerateButton());
        gptApi.waitUntilSent(1);
        assertion()
                .historySize(1, 1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(I1.INTERACTION.id()).orElseThrow())
                .historyItems(storage.readInteraction(I1.INTERACTION.id()).orElseThrow())
                .themeSize(1)
                .themeSelectedItem(I1.THEME)
                .themeItems(I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .modelEditedQuestion(I1.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I2.EXP_GRAMMAR_HTML_BODY)
                .shortA().text(I2.EXP_SHORT_HTML_BODY)
                .longA().text(I2.EXP_LONG_HTML_BODY)
                .gcpA().text(I2.EXP_GCP_HTML_BODY)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperatures(55, 60, 70, 80)
                .answerSpinnerTemperatures(55, 60, 70, 80)
                .assertApp();
    }
}