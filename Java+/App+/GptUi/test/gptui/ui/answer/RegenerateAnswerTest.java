package gptui.ui.answer;

import gptui.ui.BaseGptUiTest;
import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import gptui.ui.TestingData.I3;
import org.junit.jupiter.api.Test;

import static gptui.viewmodel.question.QuestionStyle.QUESTION_STYLE_EMPTY;
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

                .work("Click Regenerate Grammar Answer Button", () -> {
                    gptApi.clear().putGrammarResponse(I3.GRAMMAR_HTML, ZERO);
                    clickOn(grammarAnswer().temperatureIncrementButton());
                })
                .focus(grammarAnswer().temperatureSpinner())
                .answerSpinnerTemperatures(55, 60, 70, 80)

                .work("Wait for Regenerate Grammar Answer Response", () -> {
                    clickOn(grammarAnswer().regenerateButton());
                    gptApi.waitUntilSent(1);
                })
                .focus(grammarAnswer().regenerateButton())
                .historyItems(storage.readInteraction(I2.INTERACTION.id()).orElseThrow(), I1.INTERACTION)
                .grammarA().text(I3.EXP_GRAMMAR_HTML_BODY)
                .answerTextTemperatures(55, 60, 70, 80)

                .work("regenerateShortAnswer", () -> {
                    gptApi.clear().putShortResponse(I3.SHORT_HTML, ZERO);
                    clickOn(shortAnswer().regenerateButton());
                    gptApi.waitUntilSent(1);
                })
                .focus(shortAnswer().regenerateButton())
                .historyItems(storage.readInteraction(I2.INTERACTION.id()).orElseThrow(), I1.INTERACTION)
                .shortA().text(I3.EXP_SHORT_HTML_BODY)

                .work("Regenerate Long Answer", () -> {
                    gptApi.clear().putLongResponse(I3.LONG_HTML, ZERO);
                    clickOn(longAnswer().regenerateButton());
                    gptApi.waitUntilSent(1);
                })
                .focus(longAnswer().regenerateButton())
                .historyItems(storage.readInteraction(I2.INTERACTION.id()).orElseThrow(), I1.INTERACTION)
                .longA().text(I3.EXP_LONG_HTML_BODY)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)

                .work("Regenerate GCP Answer", () -> {
                    gptApi.clear().putGcpResponse(I3.GCP_HTML, ZERO);
                    clickOn(gcpAnswer().regenerateButton());
                    gptApi.waitUntilSent(1);
                })
                .focus(gcpAnswer().regenerateButton())
                .historyItems(storage.readInteraction(I2.INTERACTION.id()).orElseThrow(), I1.INTERACTION)
                .gcpA().text(I3.EXP_GCP_HTML_BODY)

                .work("Choose Interaction 1 from history", () -> {
                    clickOn(history().comboBox());
                    clickOn(String.format("[Q] %s: %s", I1.THEME.title(), I1.QUESTION));
                })
                .focus(history().comboBox())
                .historySelectedItem(storage.readInteraction(I1.INTERACTION.id()).orElseThrow())
                .themeSelectedItem(I1.THEME)
                .questionText(I1.QUESTION)
                .modelEditedQuestion(I1.QUESTION)
                .grammarA().text(I1.GRAMMAR_HTML)
                .shortA().text(I1.SHORT_HTML)
                .longA().text(I1.LONG_HTML)
                .gcpA().text(I1.GCP_HTML)
                .answerTextTemperatures(50, 60, 70, 80)

                .work("Regenerate GCP Answer", () -> {
                    gptApi.clear().putGcpResponse(I3.GCP_HTML, ZERO);
                    clickOn(gcpAnswer().regenerateButton());
                    gptApi.waitUntilSent(1);
                })
                .focus(gcpAnswer().regenerateButton())
                .historyItems(storage.readInteraction(I2.INTERACTION.id()).orElseThrow(), storage.readInteraction(I1.INTERACTION.id()).orElseThrow())
                .gcpA().text(I3.EXP_GCP_HTML_BODY)

                .assertApp();
    }

}