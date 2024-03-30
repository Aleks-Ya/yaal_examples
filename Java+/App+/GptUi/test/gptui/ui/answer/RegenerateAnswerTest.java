package gptui.ui.answer;

import gptui.ui.BaseGptUiTest;
import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import gptui.ui.TestingData.I3;
import org.junit.jupiter.api.Test;

import static gptui.viewmodel.Styles.QUESTION_STYLE_EMPTY;
import static java.time.Duration.ZERO;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

class RegenerateAnswerTest extends BaseGptUiTest {
    @Override
    public void init() {
        storage.saveTheme(I1.THEME);
        storage.saveTheme(I2.THEME);
        storage.saveInteraction(I1.INTERACTION);
        storage.saveInteraction(I2.INTERACTION);
    }

    @Test
    void currentInteractionIsTheOnly() {
        initialState();
        regenerateGrammarAnswer();
        regenerateShortAnswer();
        regenerateLongAnswer();
        regenerateGcpAnswer();
        chooseInteractionAndRegenerateGcpAnswer();
    }

    private void initialState() {
        assertion()
                .focus(history().comboBox())
                .historySize(2, 2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(I2.INTERACTION)
                .historyItems(I2.INTERACTION, I1.INTERACTION)
                .themeSize(2)
                .themeSelectedItem(I2.THEME)
                .themeItems(I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I2.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
                .modelEditedQuestion(I2.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I2.GRAMMAR_HTML)
                .shortA().text(I2.SHORT_HTML)
                .longA().text(I2.LONG_HTML)
                .gcpA().text(I2.GCP_HTML)
                .answerCircleColors(GREEN, GREEN, RED, GREEN)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(50, 60, 70, 80)
                .assertApp();
    }

    private void regenerateGrammarAnswer() {
        gptApi.clear().putGrammarResponse(I3.GRAMMAR_HTML, ZERO);
        clickOn(grammarAnswer().temperatureIncrementButton());
        assertion()
                .focus(grammarAnswer().temperatureSpinner())
                .historySize(2, 2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(I2.INTERACTION)
                .historyItems(I2.INTERACTION, I1.INTERACTION)
                .themeSize(2)
                .themeSelectedItem(I2.THEME)
                .themeItems(I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I2.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
                .modelEditedQuestion(I2.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I2.GRAMMAR_HTML)
                .shortA().text(I2.SHORT_HTML)
                .longA().text(I2.LONG_HTML)
                .gcpA().text(I2.GCP_HTML)
                .answerCircleColors(GREEN, GREEN, RED, GREEN)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(55, 60, 70, 80)
                .assertApp();

        clickOn(grammarAnswer().regenerateButton());
        gptApi.waitUntilSent(1);
        assertion()
                .focus(grammarAnswer().regenerateButton())
                .historySize(2, 2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(I2.INTERACTION.id()).orElseThrow())
                .historyItems(storage.readInteraction(I2.INTERACTION.id()).orElseThrow(), I1.INTERACTION)
                .themeSize(2)
                .themeSelectedItem(I2.THEME)
                .themeItems(I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I2.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
                .modelEditedQuestion(I2.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I3.EXP_GRAMMAR_HTML_BODY)
                .shortA().text(I2.SHORT_HTML)
                .longA().text(I2.LONG_HTML)
                .gcpA().text(I2.GCP_HTML)
                .answerCircleColors(GREEN, GREEN, RED, GREEN)
                .answerTextTemperatures(55, 60, 70, 80)
                .answerSpinnerTemperatures(55, 60, 70, 80)
                .assertApp();
    }

    private void regenerateShortAnswer() {
        gptApi.clear().putShortResponse(I3.SHORT_HTML, ZERO);
        clickOn(shortAnswer().regenerateButton());
        gptApi.waitUntilSent(1);
        assertion()
                .focus(shortAnswer().regenerateButton())
                .historySize(2, 2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(I2.INTERACTION.id()).orElseThrow())
                .historyItems(storage.readInteraction(I2.INTERACTION.id()).orElseThrow(), I1.INTERACTION)
                .themeSize(2)
                .themeSelectedItem(I2.THEME)
                .themeItems(I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I2.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
                .modelEditedQuestion(I2.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I3.EXP_GRAMMAR_HTML_BODY)
                .shortA().text(I3.EXP_SHORT_HTML_BODY)
                .longA().text(I2.LONG_HTML)
                .gcpA().text(I2.GCP_HTML)
                .answerCircleColors(GREEN, GREEN, RED, GREEN)
                .answerTextTemperatures(55, 60, 70, 80)
                .answerSpinnerTemperatures(55, 60, 70, 80)
                .assertApp();
    }

    private void regenerateLongAnswer() {
        gptApi.clear().putLongResponse(I3.LONG_HTML, ZERO);
        clickOn(longAnswer().regenerateButton());
        gptApi.waitUntilSent(1);
        assertion()
                .focus(longAnswer().regenerateButton())
                .historySize(2, 2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(I2.INTERACTION.id()).orElseThrow())
                .historyItems(storage.readInteraction(I2.INTERACTION.id()).orElseThrow(), I1.INTERACTION)
                .themeSize(2)
                .themeSelectedItem(I2.THEME)
                .themeItems(I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I2.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
                .modelEditedQuestion(I2.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I3.EXP_GRAMMAR_HTML_BODY)
                .shortA().text(I3.EXP_SHORT_HTML_BODY)
                .longA().text(I3.EXP_LONG_HTML_BODY)
                .gcpA().text(I2.GCP_HTML)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperatures(55, 60, 70, 80)
                .answerSpinnerTemperatures(55, 60, 70, 80)
                .assertApp();
    }

    private void regenerateGcpAnswer() {
        gptApi.clear().putGcpResponse(I3.GCP_HTML, ZERO);
        clickOn(gcpAnswer().regenerateButton());
        gptApi.waitUntilSent(1);
        assertion()
                .focus(gcpAnswer().regenerateButton())
                .historySize(2, 2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(I2.INTERACTION.id()).orElseThrow())
                .historyItems(storage.readInteraction(I2.INTERACTION.id()).orElseThrow(), I1.INTERACTION)
                .themeSize(2)
                .themeSelectedItem(I2.THEME)
                .themeItems(I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I2.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
                .modelEditedQuestion(I2.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I3.EXP_GRAMMAR_HTML_BODY)
                .shortA().text(I3.EXP_SHORT_HTML_BODY)
                .longA().text(I3.EXP_LONG_HTML_BODY)
                .gcpA().text(I3.EXP_GCP_HTML_BODY)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperatures(55, 60, 70, 80)
                .answerSpinnerTemperatures(55, 60, 70, 80)
                .assertApp();
    }

    private void chooseInteractionAndRegenerateGcpAnswer() {
        clickOn(history().comboBox()).clickOn(String.format("[Q] %s: %s", I1.THEME.title(), I1.QUESTION));
        assertion()
                .focus(history().comboBox())
                .historySize(2, 2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(I1.INTERACTION.id()).orElseThrow())
                .historyItems(storage.readInteraction(I2.INTERACTION.id()).orElseThrow(), I1.INTERACTION)
                .themeSize(2)
                .themeSelectedItem(I1.THEME)
                .themeItems(I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
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

        gptApi.clear().putGcpResponse(I3.GCP_HTML, ZERO);
        clickOn(gcpAnswer().regenerateButton());
        gptApi.waitUntilSent(1);
        assertion()
                .focus(gcpAnswer().regenerateButton())
                .historySize(2, 2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(I1.INTERACTION.id()).orElseThrow())
                .historyItems(storage.readInteraction(I2.INTERACTION.id()).orElseThrow(), storage.readInteraction(I1.INTERACTION.id()).orElseThrow())
                .themeSize(2)
                .themeSelectedItem(I1.THEME)
                .themeItems(I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
                .modelEditedQuestion(I1.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I1.GRAMMAR_HTML)
                .shortA().text(I1.SHORT_HTML)
                .longA().text(I1.LONG_HTML)
                .gcpA().text(I3.EXP_GCP_HTML_BODY)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(55, 60, 70, 80)
                .assertApp();
    }
}