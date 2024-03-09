package gptui.ui.history;

import gptui.ui.BaseGptUiTest;
import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

class DeleteInteractionBottomTest extends BaseGptUiTest {
    @Override
    public void init() {
        storage.saveTheme(I1.THEME);
        storage.saveTheme(I2.THEME);
        storage.saveInteraction(I1.INTERACTION);
        storage.saveInteraction(I2.INTERACTION);
    }

    @Test
    void currentInteractionIsAtBottom() {
        assertion()
                .historySize(2, 2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(I2.INTERACTION)
                .historyItems(I2.INTERACTION, I1.INTERACTION)
                .themeSize(2)
                .themeSelectedItem(I2.THEME)
                .themeItems(I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I2.QUESTION)
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

        clickOn(history().comboBox()).clickOn(format("[Q] %s: %s", I1.THEME.title(), I1.QUESTION));
        clickOn(history().deleteButton());

        assertion()
                .historySize(1, 1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(I2.INTERACTION)
                .historyItems(I2.INTERACTION)
                .themeSize(2)
                .themeSelectedItem(I2.THEME)
                .themeItems(I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I2.QUESTION)
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
}